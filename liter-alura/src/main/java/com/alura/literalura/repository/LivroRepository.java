package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    /**
     * Busca um livro pelo título, ignorando maiúsculas/minúsculas e permitindo buscas parciais.
     * Retorna um Optional para tratar o caso de o livro não ser encontrado.
     */
    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca todos os livros registrados num determinado idioma.
     * A busca é case-sensitive (diferencia maiúsculas de minúsculas).
     */
    List<Livro> findByIdioma(String idioma);

    // Um método para listar todos os livros de um determinado autor.
    // Isso seria útil para uma nova funcionalidade no menu.
    List<Livro> findByAutor(Autor autor);
}