package com.parking.administration.demo.infra.exception.enums;

public enum ErrorCode {

    ON0001("This was a bad request, check the data and try again.",
            "ON-0001"),
    FO0001("The CPF format entered is not valid.", "FO-0001"),
    ON0003("This email format is not valid.", "ON-0003"),
    ON0004("Token not found.", "ON-0004"),
    ON0005("This password format is not valid.", "ON-0005"),
    WA0001("This Parking space was not found.", "WA-0001"),
    WA0002("This Vehicle was not found.", "WA-0002"),
    WA0003("This user was not found.", "WA-0003"),
    EM0001("The e-mail has already been confirmed.", "EM-0001"),
    EM0002("This e-mail was already taken.", "EM-0001"),
    NO0001("The attempt to send the e-mail was unsuccessful", "NO-0001")
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
