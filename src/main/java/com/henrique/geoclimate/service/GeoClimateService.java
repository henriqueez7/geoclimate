package com.henrique.geoclimate.service;

import com.henrique.geoclimate.client.ViaCepClient;
import com.henrique.geoclimate.client.WeatherClient;
import com.henrique.geoclimate.dto.EnderecoResponse;
import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.dto.WeatherResponse;
import com.henrique.geoclimate.exception.CepInvalidoException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeoClimateService {

    private final ViaCepClient viaCepClient;
    private final WeatherClient weatherClient;
    private final Map<String, GeoClimateResponse> cache = new ConcurrentHashMap<>();

    public GeoClimateService(
            ViaCepClient viaCepClient,
            WeatherClient weatherClient
    ) {
        this.viaCepClient = viaCepClient;
        this.weatherClient = weatherClient;
    }

    public GeoClimateResponse buscarEnderecoEClimaPorCep(String cep) {

        validarCep(cep);

        if (cache.containsKey(cep)) {
            return cache.get(cep);
        }

        EnderecoResponse endereco = viaCepClient.buscarEndereco(cep);

        WeatherResponse clima = weatherClient.buscarClima(
                endereco.getLocalidade(),
                endereco.getUf()
        );

        GeoClimateResponse response = new GeoClimateResponse(
                endereco.getCep(),
                endereco.getLocalidade(),
                endereco.getUf(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                clima
        );

        cache.put(cep, response);

        return response;
    }

    private void validarCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new CepInvalidoException(
                    "CEP deve conter exatamente 8 dígitos numéricos."
            );
        }
    }
}