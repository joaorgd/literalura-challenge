package com.alura.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder; // Importe esta classe
import java.net.URI;

@Service
public class ConsumoApi {

    public String obterDados(String endereco, String termoBusca) {
        // Usamos UriComponentsBuilder para construir a URI de forma segura,
        // garantindo a codificação correta de caracteres especiais e espaços.
        URI uri = UriComponentsBuilder.fromUriString(endereco)
                .queryParam("search", termoBusca)
                .build()
                .toUri();

        System.out.println("DEBUG: URI final -> " + uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            System.err.println("!!!!!!!!!! ERRO AO CONSUMIR A API !!!!!!!!!!");
            e.printStackTrace();
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return null;
        }
    }
}