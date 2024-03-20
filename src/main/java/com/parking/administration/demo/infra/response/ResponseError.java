package com.parking.administration.demo.infra.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ResponseError {
    private HttpStatus status;
    private Boolean success;
    private String message;
    private String code;


    public ResponseError(HttpStatus status, Boolean success, String message, String code) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public ResponseError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
