package com.parking.administration.infra.exception;

public class TokenException extends IllegalStateException {

    private String code;

    public TokenException(String code, String message) {
        super(message);
        this.code = code;
    }
}
