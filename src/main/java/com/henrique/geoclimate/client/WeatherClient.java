package com.henrique.geoclimate.client;

import com.henrique.geoclimate.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class WeatherClient {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse buscarClima(String cidade, String estado) {

        String url =
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + cidade + "," + estado + ",BR"
                        + "&appid=" + apiKey
                        + "&units=metric"
                        + "&lang=pt_br";

        Map response = restTemplate.getForObject(url, Map.class);

        Map main = (Map) response.get("main");

        List weatherList = (List) response.get("weather");
        Map weather = (Map) weatherList.get(0);

        Double temperatura = ((Number) main.get("temp")).doubleValue();
        Integer umidade = ((Number) main.get("humidity")).intValue();

        String descricao = weather.get("description").toString();

        return new WeatherResponse(
                temperatura,
                descricao,
                umidade
        );
    }

    public WeatherResponse buscarClimaPorCoordenadas(double lat, double lon) {

        String url =
                "https://api.openweathermap.org/data/2.5/weather"
                        + "?lat=" + lat
                        + "&lon=" + lon
                        + "&appid=" + apiKey
                        + "&units=metric"
                        + "&lang=pt_br";

        Map response = restTemplate.getForObject(url, Map.class);

        Map main = (Map) response.get("main");

        List weatherList = (List) response.get("weather");
        Map weather = (Map) weatherList.get(0);

        Double temperatura = ((Number) main.get("temp")).doubleValue();
        Integer umidade = ((Number) main.get("humidity")).intValue();

        String descricao = weather.get("description").toString();

        return new WeatherResponse(
                temperatura,
                descricao,
                umidade
        );
    }

}