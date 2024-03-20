package com.parking.administration.demo.infra.exception.enums;

public enum ErrorCode {

    ON0001("It's not possible to create a new element with values that already are registered in the system",
            "ON-0001"),
    ON0002("The CPF format entered is not valid.", "ON-0002"),
    WA0001("This Parking space was not found.", "WA-0001"),
    WA0002("This Vehicle was not found.", "WA-0002"),
    WA0003("This Client was not found.", "WA-0003")
    ;
    private String message;
    private String code;

    ErrorCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
