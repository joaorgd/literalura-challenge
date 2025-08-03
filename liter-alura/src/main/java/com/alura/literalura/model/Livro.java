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

    // @ManyToOne: Define a relação "muitos livros para um autor".
    // Esta é a entidade "dona" do relacionamento, onde a chave estrangeira (autor_id) será criada.
    @ManyToOne
    private Autor autor;

    // Construtor padrão exigido pelo JPA
    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idiomas().isEmpty() ? "desconhecido" : dadosLivro.idiomas().get(0);
    }

    @Override
    public String toString() {
        return "---------- LIVRO ----------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNome() : "Desconhecido") +
                "\nIdioma: " + idioma +
                "\n---------------------------";
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}