package com.lab1.converter.exceptions;

public class ConversionNotFoundException extends RuntimeException {
    public ConversionNotFoundException(Long id) {
        super("Conversion with id=" + id + " not found");
    }
}
