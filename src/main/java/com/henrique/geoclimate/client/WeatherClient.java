package com.henrique.geoclimate.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrique.geoclimate.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WeatherClient {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherResponse buscarClima(String cidade, String estado) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(weatherApiUrl)
                    .queryParam("q", cidade + "," + estado + ",BR")
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .queryParam("lang", "pt_br")
                    .build()
                    .encode()
                    .toUri();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao consultar clima");
            }

            JsonNode json = objectMapper.readTree(response.body());

            return new WeatherResponse(
                    json.get("main").get("temp").asDouble(),
                    json.get("weather").get(0).get("description").asText(),
                    json.get("main").get("humidity").asInt()
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar clima", e);
        }
    }
}