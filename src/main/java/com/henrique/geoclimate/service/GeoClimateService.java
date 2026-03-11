package com.henrique.geoclimate.service;

import com.henrique.geoclimate.client.ViaCepClient;
import com.henrique.geoclimate.client.WeatherClient;
import com.henrique.geoclimate.dto.EnderecoResponse;
import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.dto.WeatherResponse;
import com.henrique.geoclimate.exception.CepInvalidoException;
import com.henrique.geoclimate.exception.CepNotFoundException;
import com.henrique.geoclimate.model.Consulta;
import com.henrique.geoclimate.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeoClimateService {

    private final ViaCepClient viaCepClient;
    private final WeatherClient weatherClient;
    private final ConsultaRepository consultaRepository;

    private final Map<String, GeoClimateResponse> cache = new ConcurrentHashMap<>();

    public GeoClimateService(
            ViaCepClient viaCepClient,
            WeatherClient weatherClient,
            ConsultaRepository consultaRepository
    ) {
        this.viaCepClient = viaCepClient;
        this.weatherClient = weatherClient;
        this.consultaRepository = consultaRepository;
    }

    public GeoClimateResponse buscarEnderecoEClimaPorCep(String cep) {

        validarCep(cep);

        if (cache.containsKey(cep)) {
            return cache.get(cep);
        }

        EnderecoResponse endereco = viaCepClient.buscarEndereco(cep);

        if (endereco == null || Boolean.TRUE.equals(endereco.getErro())) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        }

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

        Consulta consulta = new Consulta(
                endereco.getCep(),
                endereco.getLocalidade(),
                endereco.getUf(),
                clima.temperatura(),
                clima.descricao(),
                LocalDateTime.now()
        );

        consultaRepository.save(consulta);

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

    public List<Consulta> listarHistorico() {
        return consultaRepository.findAllByOrderByDataHoraDesc();
    }

    public GeoClimateResponse buscarClimaPorCoordenadas(double lat, double lon) {

        WeatherResponse clima = weatherClient.buscarClimaPorCoordenadas(lat, lon);

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat="
                + lat + "&lon=" + lon + "&addressdetails=1";

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("User-Agent", "GeoClimateApp/1.0");

        org.springframework.http.HttpEntity<String> entity =
                new org.springframework.http.HttpEntity<>(headers);

        org.springframework.http.ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        org.springframework.http.HttpMethod.GET,
                        entity,
                        Map.class
                );

        Map body = response.getBody();
        Map address = (Map) body.get("address");

        String cidade = address.getOrDefault("city",
                address.getOrDefault("town",
                        address.getOrDefault("village", "Cidade não encontrada")
                )
        ).toString();

        String estado = address.getOrDefault("state", "").toString();
        String bairro = address.getOrDefault("suburb", "Bairro não informado").toString();
        String rua = address.getOrDefault("road", "Rua não informada").toString();

        return new GeoClimateResponse(
                "",
                cidade,
                estado,
                rua,
                bairro,
                clima
        );
    }

    public void limparHistorico() {
        consultaRepository.deleteAll();
    }

}