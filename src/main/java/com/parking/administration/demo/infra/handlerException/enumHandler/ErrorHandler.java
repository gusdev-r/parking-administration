package com.parking.administration.demo.infra.handlerException.enumHandler;

public enum ErrorHandler {


    ON0001("Bad request, revise the data and try again.",
            "ON-0001"),
    ON0002("Invalid format, revise the data and try again.",
            "ON-0002"),
    ;
    private String message;
    private String code;

    ErrorHandler(String message, String code) {
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
