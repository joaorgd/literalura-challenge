package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.LivroService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroService livroService;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            imprimirMenu();
            opcao = lerOpcaoDoMenu();

            switch (opcao) {
                case 1:
                    buscarLivroWebESalvar();
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
                    System.out.println("Encerrando o LiterAlura... Até a próxima!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // Extrair a impressão do menu e a leitura da opção para métodos separados.
    private void imprimirMenu() {
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
    }

    private int lerOpcaoDoMenu() {
        try {
            int opcao = leitura.nextInt();
            leitura.nextLine(); // Limpa o buffer
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            leitura.nextLine(); // Limpa o buffer em caso de erro
            return -1; // Retorna um valor inválido para o loop continuar
        }
    }

    private void buscarLivroWebESalvar() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var nomeLivro = leitura.nextLine();
        livroService.buscarESalvarLivroPeloTitulo(nomeLivro);
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        imprimirLista(livros, "Nenhum livro registrado.", "\n--- LIVROS REGISTRADOS ---");
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        imprimirLista(autores, "Nenhum autor registrado.", "\n--- AUTORES REGISTRADOS ---");
    }

    // Método genérico para imprimir listas, aplicando o princípio DRY.
    private <T> void imprimirLista(List<T> lista, String mensagemVazia, String cabecalho) {
        if (lista.isEmpty()) {
            System.out.println(mensagemVazia);
        } else {
            System.out.println(cabecalho);
            lista.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano para buscar autores vivos:");
        try {
            int ano = leitura.nextInt();
            leitura.nextLine();
            List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);
            imprimirLista(autores, "Nenhum autor vivo encontrado para o ano de " + ano, "\n--- AUTORES VIVOS EM " + ano + " ---");
        } catch (InputMismatchException e) {
            System.out.println("Ano inválido. Por favor, digite um número.");
            leitura.nextLine();
        }
    }

    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("Digite o idioma para a busca (ex: pt, en, es, fr):");
        var idioma = leitura.nextLine().toLowerCase();
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        imprimirLista(livros, "Nenhum livro encontrado para o idioma '" + idioma + "'.", "\n--- LIVROS EM '" + idioma.toUpperCase() + "' ---");
    }
}