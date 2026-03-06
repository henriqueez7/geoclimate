package com.henrique.geoclimate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoResponse {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    // 👇 Campo que o ViaCep retorna quando o CEP não existe
    private Boolean erro;

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public Boolean getErro() {
        return erro;
    }
}