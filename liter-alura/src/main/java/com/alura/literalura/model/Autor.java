package com.alura.literalura.model;

import com.alura.literalura.dto.DadosAutor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private Integer anoNascimento;
    private Integer anoFalecimento;

    // @OneToMany: Define a relação "um autor para muitos livros".
    // mappedBy = "autor": Indica que a entidade Livro é a dona do relacionamento.
    // cascade = CascadeType.ALL: Operações no Autor (salvar, deletar) serão cascateadas para seus Livros.
    // fetch = FetchType.LAZY: Os livros só serão carregados do banco quando acessados diretamente (ótimo para performance).
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    @Override
    public String toString() {
        // Obtém apenas os títulos dos livros para evitar carregar a lista inteira e causar erros
        // ou loops infinitos de impressão.
        String titulosLivros = livros.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.joining(", "));

        return "---------- AUTOR ----------" +
                "\nNome: " + nome +
                "\nAno de Nascimento: " + anoNascimento +
                "\nAno de Falecimento: " + anoFalecimento +
                "\nLivros: [" + titulosLivros + "]" +
                "\n---------------------------";
    }

    // --- Métodos Helper para sincronizar o relacionamento ---
    public void addLivro(Livro livro) {
        this.livros.add(livro);
        livro.setAutor(this);
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
    public List<Livro> getLivros() { return livros; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }
}