package com.lab1.converter.exceptions;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyNotFoundExceptionTest {

    @Test
    void testConstructor() {
        String currencyCode = "USD";

        CurrencyNotFoundException e = new CurrencyNotFoundException(currencyCode);

        assertEquals("Currency with code=" + currencyCode + " not found", e.getMessage());
    }
}