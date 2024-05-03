package com.parking.administration.infra.exception;

public class EmailNotValidException extends RuntimeException {
    private String code;

    public EmailNotValidException(String code, String message) {
        super(message);
        this.code = code;
    }
}
