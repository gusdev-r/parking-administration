package com.parking.administration.demo.infra.exception;

public class ParkingSpaceNotFoundException extends RuntimeException {
    private String code;

    public ParkingSpaceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
