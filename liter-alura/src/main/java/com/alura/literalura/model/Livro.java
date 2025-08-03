package com.alura.literalura.model;

import com.alura.literalura.dto.DadosLivro;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idioma;
    private Integer totalDownloads;

    @ManyToOne
    private Autor autor;

    // Construtor padrão
    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idiomas().isEmpty() ? "desconhecido" : dadosLivro.idiomas().get(0);
    }

    // Getters, Setters e toString()
    // ...

    @Override
    public String toString() {
        return "------ LIVRO ------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNome() : "Desconhecido") +
                "\nIdioma: " + idioma +
                "\n-------------------";
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getTotalDownloads() { return totalDownloads; }
    public void setTotalDownloads(Integer totalDownloads) { this.totalDownloads = totalDownloads; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}