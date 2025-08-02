package com.alura.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumoApi {

    public String obterDados(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            // Lançar uma exceção personalizada ou logar o erro seria o ideal aqui
            throw new RuntimeException("Erro ao consumir a API: " + e.getMessage(), e);
        }
    }
}