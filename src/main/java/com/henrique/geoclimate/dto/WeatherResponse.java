package com.henrique.geoclimate.dto;

public record WeatherResponse(
        Double temperatura,
        String descricao,
        Integer umidade
) {
}