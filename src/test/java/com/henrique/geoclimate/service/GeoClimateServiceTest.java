package com.henrique.geoclimate.service;

import com.henrique.geoclimate.client.ViaCepClient;
import com.henrique.geoclimate.client.WeatherClient;
import com.henrique.geoclimate.dto.EnderecoResponse;
import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.dto.WeatherResponse;
import com.henrique.geoclimate.exception.CepInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeoClimateServiceTest {

    private ViaCepClient viaCepClient;
    private WeatherClient weatherClient;
    private GeoClimateService geoClimateService;

    @BeforeEach
    void setup() {
        viaCepClient = mock(ViaCepClient.class);
        weatherClient = mock(WeatherClient.class);
        geoClimateService = new GeoClimateService(viaCepClient, weatherClient);
    }

    @Test
    void deveRetornarEnderecoEClimaQuandoCepValido() {

        String cep = "01001000";

        EnderecoResponse enderecoMock = mock(EnderecoResponse.class);

        when(enderecoMock.getCep()).thenReturn(cep);
        when(enderecoMock.getLocalidade()).thenReturn("São Paulo");
        when(enderecoMock.getUf()).thenReturn("SP");
        when(enderecoMock.getLogradouro()).thenReturn("Praça da Sé");
        when(enderecoMock.getBairro()).thenReturn("Sé");

        WeatherResponse climaMock = new WeatherResponse(
                25.0,
                "Ensolarado",
                60
        );

        when(viaCepClient.buscarEndereco(cep)).thenReturn(enderecoMock);
        when(weatherClient.buscarClima("São Paulo", "SP")).thenReturn(climaMock);

        GeoClimateResponse response =
                geoClimateService.buscarEnderecoEClimaPorCep(cep);

        assertNotNull(response);
        assertEquals("São Paulo", response.getCidade());
        assertEquals("SP", response.getEstado());
        assertEquals(25.0, response.getClima().temperatura());

        verify(viaCepClient, times(1)).buscarEndereco(cep);
        verify(weatherClient, times(1)).buscarClima("São Paulo", "SP");
    }

    @Test
    void deveLancarExcecaoQuandoCepInvalido() {

        assertThrows(
                CepInvalidoException.class,
                () -> geoClimateService.buscarEnderecoEClimaPorCep("123")
        );

        verifyNoInteractions(viaCepClient);
        verifyNoInteractions(weatherClient);
    }

    @Test
    void naoDeveChamarApisExternasQuandoCepJaEstiverNoCache() {

        String cep = "01001000";

        EnderecoResponse enderecoMock = mock(EnderecoResponse.class);

        when(enderecoMock.getCep()).thenReturn(cep);
        when(enderecoMock.getLocalidade()).thenReturn("São Paulo");
        when(enderecoMock.getUf()).thenReturn("SP");

        WeatherResponse climaMock = new WeatherResponse(
                25.0,
                "Ensolarado",
                60
        );

        when(viaCepClient.buscarEndereco(cep)).thenReturn(enderecoMock);
        when(weatherClient.buscarClima("São Paulo", "SP")).thenReturn(climaMock);

        geoClimateService.buscarEnderecoEClimaPorCep(cep);
        geoClimateService.buscarEnderecoEClimaPorCep(cep);

        verify(viaCepClient, times(1)).buscarEndereco(cep);
        verify(weatherClient, times(1)).buscarClima("São Paulo", "SP");
    }
}