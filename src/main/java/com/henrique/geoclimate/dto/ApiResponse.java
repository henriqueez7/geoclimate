package com.henrique.geoclimate.dto;

public class ApiResponse<T> {

    private boolean sucesso;
    private T dados;
    private String erro;

    public ApiResponse(T dados) {
        this.sucesso = true;
        this.dados = dados;
    }

    public ApiResponse(String erro) {
        this.sucesso = false;
        this.erro = erro;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public T getDados() {
        return dados;
    }

    public String getErro() {
        return erro;
    }
}