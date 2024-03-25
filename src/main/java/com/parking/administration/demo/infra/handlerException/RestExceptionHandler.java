package com.parking.administration.demo.infra.handlerException;

import com.parking.administration.demo.infra.exception.*;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.infra.handlerException.enumHandler.ErrorHandler;
import com.parking.administration.demo.infra.response.ResponseError;
import org.springframework.http.HttpStatus;
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
                ErrorHandler.ON0001.getMessage(), ErrorHandler.ON0001.getCode());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    private ResponseError handlerBadRequest(BadRequestException badRequestException) {
        return new ResponseError(HttpStatus.BAD_REQUEST, false,
                ErrorHandler.ON0001.getMessage(), ErrorHandler.ON0001.getCode());
    }
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(DocumentNotValidException.class)
    private ResponseError cpfNotValid(DocumentNotValidException documentNotValidException) {
        return new ResponseError(HttpStatus.NOT_ACCEPTABLE, false,
                ErrorHandler.ON0002.getMessage(), ErrorHandler.ON0002.getCode());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseError userNotFound(UserNotFoundException userNotFoundException) {
        return new ResponseError(HttpStatus.NOT_FOUND, false,
                ErrorHandler.ON0001.getMessage(), ErrorHandler.ON0001.getCode());
    }
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(EmailNotValidException.class)
    private ResponseError userNotFound(EmailNotValidException userNotFoundException) {
        return new ResponseError(HttpStatus.NOT_ACCEPTABLE, false,
                ErrorHandler.ON0002.getMessage(), ErrorHandler.ON0002.getCode());
    }

}
