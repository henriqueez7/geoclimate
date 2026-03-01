package com.henrique.geoclimate.controller;

import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.dto.WeatherResponse;
import com.henrique.geoclimate.service.GeoClimateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeoClimateController.class)
class GeoClimateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoClimateService geoClimateService;

    @Test
    void deveRetornar200QuandoCepValido() throws Exception {

        WeatherResponse clima = new WeatherResponse(
                25.0,
                "Ensolarado",
                60
        );

        GeoClimateResponse response = new GeoClimateResponse(
                "01001000",
                "São Paulo",
                "SP",
                "Praça da Sé",
                "Sé",
                clima
        );

        when(geoClimateService.buscarEnderecoEClimaPorCep("01001000"))
                .thenReturn(response);

        mockMvc.perform(get("/api/geoclimate/01001000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.estado").value("SP"))
                .andExpect(jsonPath("$.clima.temperatura").value(25.0));
    }
}