package com.lab1.converter.exceptions;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String code) {
        super("Currency with code=" + code + " not found");
    }
}
