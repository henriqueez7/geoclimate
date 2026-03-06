package com.henrique.geoclimate.controller;

import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.model.Consulta;
import com.henrique.geoclimate.service.GeoClimateService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoclimate")
public class GeoClimateController {

    private final GeoClimateService service;

    public GeoClimateController(GeoClimateService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    public GeoClimateResponse consultar(@PathVariable String cep) {
        return service.buscarEnderecoEClimaPorCep(cep);
    }

    @GetMapping("/historico")
    public List<Consulta> historico() {
        return service.listarHistorico();
    }

    @GetMapping("/health")
    public String health() {
        return "API GeoClimate funcionando!";
    }
}