package com.parking.administration.demo.infra.exception;

public class EmailNotSendException extends RuntimeException {
    private String code;

    public EmailNotSendException(String code, String message) {
        super(message);
        this.code = code;
    }
}
