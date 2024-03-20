package com.parking.administration.demo.infra.handlerException;

import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.CpfNotValidException;
import com.parking.administration.demo.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.infra.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(ParkingSpaceNotFoundException.class)
    private ResponseError parkingSpaceNotFound(ParkingSpaceNotFoundException parkingSpaceNotFoundException) {
        return new ResponseError(HttpStatus.NOT_FOUND, false,
                ErrorCode.WA0001.getMessage(), ErrorCode.WA0001.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    private ResponseError handlerBadRequest(BadRequestException badRequestException) {
        return new ResponseError(HttpStatus.BAD_REQUEST, false,
                ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
    }
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    private ResponseError cpfNotValid(CpfNotValidException cpfNotValidException) {
        return new ResponseError(HttpStatus.NOT_ACCEPTABLE, false,
                ErrorCode.ON0002.getMessage(), ErrorCode.ON0002.getCode());
    }


}
