package com.lab1.converter.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("ERROR 404 - " + e.getMessage());
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse handleMethodNotAllowedException(Exception e) {
        log.error("ERROR 405 - " + e.getMessage());
        return new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerErrorException(RuntimeException e) {
        log.error("ERROR 500 - " + e.getMessage());
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler({HttpClientErrorException.class, HttpMessageNotReadableException.class,
                       MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
                       ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(Exception e) {
        log.error("ERROR 400 - Bad request");
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Bad request");
    }

    @ExceptionHandler({UserNotFoundException.class, ConversionNotFoundException.class,
                       CurrencyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFoundException(RuntimeException e) {
        log.error("ERROR 404 - " + e.getMessage());
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}