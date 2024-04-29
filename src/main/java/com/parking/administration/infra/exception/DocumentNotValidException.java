package com.parking.administration.infra.exception;

public class DocumentNotValidException extends RuntimeException {
    private String code;

    public DocumentNotValidException(String code, String message) {
        super(message);
        this.code = code;
    }
}
