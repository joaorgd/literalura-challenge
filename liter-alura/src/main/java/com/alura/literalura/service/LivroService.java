package com.alura.literalura.service;

import com.alura.literalura.dto.DadosAutor;
import com.alura.literalura.dto.DadosLivro;
import com.alura.literalura.dto.ResultadosDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LivroService {

    // Endereço da API (agora mora no serviço que a consome)
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    // Injetamos todas as dependências que a lógica de negócio precisa
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados conversor;

    // A anotação @Transactional garante que a operação com o banco será executada em uma única transação.
    @Transactional
    public void buscarESalvarLivroPeloTitulo(String nomeLivro) {
        // 1. BUSCAR DADOS NA API
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        ResultadosDTO resultados = conversor.obterDados(json, ResultadosDTO.class);

        if (resultados == null || resultados.livros().isEmpty()) {
            System.out.println("Livro não encontrado com o título '" + nomeLivro + "'.");
            return;
        }

        DadosLivro dadosLivro = resultados.livros().get(0);

        // 2. VERIFICAR SE O LIVRO JÁ EXISTE NO BANCO
        Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo());
        if (livroExistente.isPresent()) {
            System.out.println("Este livro já está cadastrado no banco de dados.");
            return;
        }

        // 3. VERIFICAR E CADASTRAR O AUTOR (SE NECESSÁRIO)
        Autor autor;
        if (dadosLivro.autores().isEmpty()) {
            // Se o livro não tiver autor na API, podemos atribuir um autor "Desconhecido"
            // ou simplesmente não cadastrar. Vamos pular o cadastro por enquanto.
            System.out.println("O livro encontrado não possui autor, não será salvo.");
            return;
        }

        DadosAutor dadosAutor = dadosLivro.autores().get(0);
        Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(dadosAutor.nome());

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor(dadosAutor);
            autorRepository.save(autor); // Salvamos o novo autor
        }

        // 4. CRIAR E SALVAR O NOVO LIVRO
        Livro novoLivro = new Livro(dadosLivro);
        novoLivro.setAutor(autor); // Associamos o autor ao livro
        livroRepository.save(novoLivro);

        System.out.println("Livro salvo com sucesso!");
        System.out.println(novoLivro);
    }
}