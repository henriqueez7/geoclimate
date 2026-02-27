package com.henrique.geoclimate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrique.geoclimate.dto.EnderecoResponse;
import com.henrique.geoclimate.exception.CepNaoEncontradoException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ViaCepClient {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ViaCepClient(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    public EnderecoResponse buscarEndereco(String cep) {

        try {
            String url = String.format(VIA_CEP_URL, cep);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            EnderecoResponse endereco =
                    objectMapper.readValue(response.body(), EnderecoResponse.class);

            if (response.body().contains("\"erro\": true")) {
                throw new CepNaoEncontradoException("CEP não encontrado.");
            }

            return endereco;

        } catch (CepNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ViaCEP", e);
        }
    }
}