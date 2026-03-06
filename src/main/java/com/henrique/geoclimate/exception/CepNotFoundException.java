package com.henrique.geoclimate.exception;

public class CepNotFoundException extends RuntimeException {

    public CepNotFoundException(String message) {
        super(message);
    }
}