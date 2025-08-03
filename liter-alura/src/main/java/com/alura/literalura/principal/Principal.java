package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.LivroService;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);

    // Agora, a classe Principal só precisa conhecer seus repositórios e o novo serviço.
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroService livroService;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n*** BEM-VINDO AO LITERALURA ***
                    Escolha uma das opções abaixo:
                    
                    1 - Buscar livro pelo título e salvar
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
                    buscarLivroWebESalvar(); // Nome do método ficou mais claro
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

    // Veja como este método ficou mais simples e legível!
    private void buscarLivroWebESalvar() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var nomeLivro = leitura.nextLine();
        livroService.buscarESalvarLivroPeloTitulo(nomeLivro); // Apenas chama o serviço
    }

    // Os outros métodos que consultam diretamente o banco podem permanecer aqui
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