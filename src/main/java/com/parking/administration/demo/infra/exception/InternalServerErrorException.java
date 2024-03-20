package com.parking.administration.demo.infra.exception;

public class InternalServerErrorException extends RuntimeException {

    private String code;

    public InternalServerErrorException(String code, String message) {
        super(message);
        this.code = code;
    }
}
