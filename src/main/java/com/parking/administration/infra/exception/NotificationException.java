package com.parking.administration.infra.exception;

public class NotificationException extends Exception {

    private String code;

    public NotificationException(String code, String message) {
        super(message);
        this.code = code;
    }
}
