package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    /**
     * Busca um autor pelo nome, ignorando maiúsculas/minúsculas e permitindo buscas parciais.
     * Retorna um Optional, pois o autor pode não ser encontrado.
     */
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca todos os autores que estavam vivos em um determinado ano.
     * A lógica verifica se o ano de nascimento é menor ou igual ao ano fornecido
     * e se o ano de falecimento é nulo (ainda vivo) ou maior ou igual ao ano fornecido.
     * Utiliza uma query JPQL customizada para lógica mais complexa.
     */
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(int ano);

    // Um método para buscar os 10 autores mais famosos (com mais livros registrados)
    @Query("SELECT a FROM Autor a JOIN a.livros l GROUP BY a ORDER BY COUNT(l) DESC LIMIT 10")
    List<Autor> findTop10AutoresComMaisLivros();
}