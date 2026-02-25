package com.henrique.geoclimate.dto;

public class GeoClimateResponse {

    private String cep;
    private String cidade;
    private String estado;
    private String logradouro;
    private String bairro;
    private WeatherResponse clima;

    public GeoClimateResponse(
            String cep,
            String cidade,
            String estado,
            String logradouro,
            String bairro,
            WeatherResponse clima
    ) {
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.clima = clima;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public WeatherResponse getClima() {
        return clima;
    }
}