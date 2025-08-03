package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Representa a estrutura de nível mais alto da resposta da API Gutendex.
 * A API não retorna uma lista de livros diretamente, mas um objeto JSON
 * que contém uma chave "results" com a lista de livros.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora todos os outros campos que não mapeamos, como "count", "next", etc.
public record ResultadosDTO(
        // Mapeia o campo "results" do JSON para a nossa lista "livros".
        @JsonAlias("results") List<DadosLivro> livros
) {}