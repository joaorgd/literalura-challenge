// Pacote: com.alura.literalura.principal
package com.alura.literalura.principal;

import com.alura.literalura.dto.DadosLivro;
import com.alura.literalura.dto.ResultadosDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books";

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n*** BEM-VINDO AO LITERALURA ***
                    Escolha uma das opções abaixo:
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); // Limpar o buffer
            } catch (Exception e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                leitura.nextLine(); // Limpar o buffer em caso de erro
                continue;
            }


            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo do LiterAlura...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));

        ResultadosDTO resultados = conversor.obterDados(json, ResultadosDTO.class);

        if (resultados != null && resultados.livros() != null && !resultados.livros().isEmpty()) {
            DadosLivro dadosLivro = resultados.livros().get(0); // Pega o primeiro livro da busca

            // Verifica se o livro já existe no banco
            Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo());
            if(livroExistente.isPresent()) {
                System.out.println("Este livro já está cadastrado no banco de dados.");
                return;
            }

            // Verifica se o autor já existe no banco
            Autor autor;
            Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(dadosLivro.autores().get(0).nome());

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor(dadosLivro.autores().get(0));
                autor = autorRepository.save(autor);
            }

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);
            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso!");
            System.out.println(livro);

        } else {
            System.out.println("Livro não encontrado com esse título.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("\n--- LIVROS REGISTRADOS ---");
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano para buscar autores vivos:");
        try {
            int ano = leitura.nextInt();
            leitura.nextLine(); // Limpar buffer
            List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado para o ano de " + ano);
            } else {
                System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
                autores.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Ano inválido. Por favor, digite um número.");
            leitura.nextLine();
        }
    }

    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("Digite o idioma para a busca (ex: pt, en, es, fr):");
        var idioma = leitura.nextLine();
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if(livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma '" + idioma + "'." );
        } else {
            System.out.println("\n--- LIVROS EM '" + idioma.toUpperCase() + "' ---");
            livros.forEach(System.out::println);
        }
    }
}