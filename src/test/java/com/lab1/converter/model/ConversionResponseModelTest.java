package com.lab1.converter.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionResponseModelTest {

    @Test
    void testGettersAndSetters() {
        ConversionResponseModel model = new ConversionResponseModel();

        model.setResult("success");
        model.setConversionResult(5.0);

        assertEquals("success", model.getResult());
        assertEquals(5.0, model.getConversionResult());
    }
}
