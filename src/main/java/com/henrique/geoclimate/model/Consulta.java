package com.henrique.geoclimate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    private String cidade;

    private String estado;

    private LocalDateTime dataConsulta;

    public Consulta(String cep, String localidade, String uf, Double temperatura, String descricao, LocalDateTime now) {}

    public Consulta(String cep, String cidade, String estado) {
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.dataConsulta = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }
}