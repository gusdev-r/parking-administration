package com.parking.administration.infra.exception;

public class PasswordNotValidException extends RuntimeException {
    private String code;

    public PasswordNotValidException(String code, String message) {
        super(message);
        this.code = code;
    }
}
