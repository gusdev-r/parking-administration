package com.parking.administration.infra.exception;

public class VehicleNotFoundException extends RuntimeException {
    private String code;

    public VehicleNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
