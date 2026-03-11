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
    private Double temperatura;
    private String clima;
    private LocalDateTime dataHora;

    public Consulta() {
    }

    public Consulta(String cep, String cidade, String estado, Double temperatura, String clima, LocalDateTime dataHora) {
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.temperatura = temperatura;
        this.clima = clima;
        this.dataHora = dataHora;
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

    public Double getTemperatura() {
        return temperatura;
    }

    public String getClima() {
        return clima;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}