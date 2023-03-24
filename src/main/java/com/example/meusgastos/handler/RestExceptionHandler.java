package com.example.meusgastos.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.meusgastos.common.FormatDate;
import com.example.meusgastos.domain.exception.ResourceBadRequestException;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException exception) {

        String dateAndHour = FormatDate.formatDate(new Date());

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerResourceBadRequestException(ResourceBadRequestException exception) {

        String dateAndHour = FormatDate.formatDate(new Date());

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerRequestException(Exception exception) {

        String dateAndHour = FormatDate.formatDate(new Date());

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}