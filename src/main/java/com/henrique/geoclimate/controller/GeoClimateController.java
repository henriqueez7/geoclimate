package com.henrique.geoclimate.controller;

import com.henrique.geoclimate.dto.GeoClimateResponse;
import com.henrique.geoclimate.dto.WeatherResponse;
import com.henrique.geoclimate.service.GeoClimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geoclimate")
public class GeoClimateController {

    private final GeoClimateService geoClimateService;

    public GeoClimateController(GeoClimateService geoClimateService) {
        this.geoClimateService = geoClimateService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<GeoClimateResponse> buscarPorCep(@PathVariable String cep) {
        GeoClimateResponse response =
                geoClimateService.buscarEnderecoEClimaPorCep(cep);
        return ResponseEntity.ok(response);
    }
}