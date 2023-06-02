package com.example.myexpenses.handler;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.myexpenses.common.FormatDate;
import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.exception.ResourceNotFoundException;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException exception) {

        String dateAndHour = FormatDate.formatDate(new Date());

        String message = exception.getMessage();

        if (message.contains("uk_6dotkott2kjsp8vw4d0m25fb7")) {
            message = "Já existe um usuário cadastrado com este email";
        }

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        String dateAndHour = FormatDate.formatDate(new Date());

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}