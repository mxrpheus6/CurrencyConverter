package com.lab1.converter.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeRateApiResponseModelTest {

    @Test
    void testGettersAndSetters() {
        ExchangeRateApiResponseModel model = new ExchangeRateApiResponseModel();
        Map<String, Double> rates = new HashMap<>();
        rates.put("BYN", 3.25);
        rates.put("USD", 1.0);

        model.setResult("success");
        model.setConversionRates(rates);

        assertEquals("success", model.getResult());
        assertEquals(3.25, model.getConversionRates().get("BYN"));
        assertEquals(1.0, model.getConversionRates().get("USD"));
    }
}
