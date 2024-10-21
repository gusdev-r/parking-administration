package com.parking.administration.infra.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseError {
    private HttpStatus status;
    private Boolean success;
    private String message;
    private String code;

    public ResponseError(HttpStatus status, Boolean success, String message,
                         String code) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.code = code;
    }
}
