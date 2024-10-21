package com.parking.administration.infra.exception;

public class ClientNotFoundException extends RuntimeException {
    private String code;

    public ClientNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
