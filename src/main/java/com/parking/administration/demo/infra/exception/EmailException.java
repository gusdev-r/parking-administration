package com.parking.administration.demo.infra.exception;

public class EmailException extends RuntimeException {
    private String code;

    public EmailException(String code, String message) {
        super(message);
        this.code = code;
    }
}
