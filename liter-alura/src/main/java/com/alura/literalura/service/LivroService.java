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
// CORREÇÃO: Importação correta da anotação Transactional para o Spring
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LivroService {
    // A constante agora é apenas o endereço base, sem o parâmetro de busca.
    private final String ENDERECO_BASE = "https://gutendex.com/books/";

    // Injeção de dependências que serão usadas pelo serviço
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados conversor;

    // A anotação @Transactional garante que a operação com o banco será executada em uma única transação.
    // Em LivroService.java
    @Transactional
    public void buscarESalvarLivroPeloTitulo(String nomeLivro) {
        // A chamada agora passa o endereço base e o termo de busca separadamente.
        var json = consumoApi.obterDados(ENDERECO_BASE, nomeLivro);

        if (json == null || json.isEmpty()) {
            System.out.println("A API não retornou um resultado válido.");
            return;
        }

        // Usaremos um Optional para tratar a resposta da API com mais segurança
        Optional<DadosLivro> livroEncontradoOpt = conversor.obterDados(json, ResultadosDTO.class)
                .livros().stream()
                .findFirst(); // Pega o primeiro livro da lista, se houver

        // 2. VERIFICA SE UM LIVRO FOI REALMENTE ENCONTRADO
        if (livroEncontradoOpt.isPresent()) {
            DadosLivro dadosLivro = livroEncontradoOpt.get();

            // 3. VERIFICAR SE O LIVRO JÁ EXISTE NO BANCO
            Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo());
            if (livroExistente.isPresent()) {
                System.out.println("O livro '" + dadosLivro.titulo() + "' já está cadastrado.");
                return;
            }

            // 4. VERIFICAR E CADASTRAR O AUTOR (SE NECESSÁRIO)
            if (dadosLivro.autores().isEmpty()) {
                System.out.println("O livro '" + dadosLivro.titulo() + "' não possui autor na API e não será salvo.");
                return;
            }

            Autor autor;
            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(dadosAutor.nome());

            autor = autorExistente.orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));

            // 5. CRIAR E SALVAR O NOVO LIVRO
            Livro novoLivro = new Livro(dadosLivro);
            novoLivro.setAutor(autor);
            livroRepository.save(novoLivro);

            System.out.println("Livro salvo com sucesso!");
            System.out.println(novoLivro);

        } else {
            // Se o Optional estiver vazio, significa que a lista de resultados da API veio vazia.
            System.out.println("Nenhum livro encontrado para o título '" + nomeLivro + "'.");
        }
    }
}