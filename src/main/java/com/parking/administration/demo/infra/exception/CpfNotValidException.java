package com.parking.administration.demo.infra.exception;

public class CpfNotValidException extends RuntimeException {
    private String code;

    public CpfNotValidException(String code, String message) {
        super(message);
        this.code = code;
    }
}
