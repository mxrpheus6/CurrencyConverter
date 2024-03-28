package com.lab1.converter.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleNoHandlerFoundException() {
        NoHandlerFoundException exception = new NoHandlerFoundException("GET", "/test", null);

        ExceptionResponse response = globalExceptionHandler.handleNoHandlerFoundException(exception);

        assertEquals(404, response.getStatusCode());
        assertEquals("No endpoint GET /test.", response.getMessage());
    }

    @Test
    void testHandleMethodNotAllowedException() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("GET");

        ExceptionResponse response = globalExceptionHandler.handleMethodNotAllowedException(exception);

        assertEquals(405, response.getStatusCode());
        assertEquals("Request method 'GET' is not supported", response.getMessage());
    }

    @Test
    void testHandleInternalServerErrorException() {
        RuntimeException exception = new RuntimeException("Request method 'GET' is not supported");

        ExceptionResponse response = globalExceptionHandler.handleInternalServerErrorException(exception);

        assertEquals(500, response.getStatusCode());
        assertEquals("Request method 'GET' is not supported", response.getMessage());
    }

    @Test
    void testHandleBadRequestException() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Bad request");

        ExceptionResponse response = globalExceptionHandler.handleBadRequestException(exception);

        assertEquals(400, response.getStatusCode());
        assertEquals("Bad request", response.getMessage());
    }

    @Test
    void testHandleResourceNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException(1L);

        ExceptionResponse response = globalExceptionHandler.handleResourceNotFoundException(exception);

        assertEquals(404, response.getStatusCode());
        assertEquals("User with id=1 not found", response.getMessage());
    }
}
