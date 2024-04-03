package com.lab1.converter.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionNotFoundExceptionTest {

    @Test
    void testConstructor() {
        Long conversionId = 456L;

        ConversionNotFoundException e = new ConversionNotFoundException(conversionId);

        assertEquals("Conversion with id=" + conversionId + " not found", e.getMessage());
    }
}