package com.lab1.converter.entity;

import com.lab1.converter.dao.CurrencyRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest {
    @Test
    void testCurrencyGettersAndSetters() {
        Currency currency = new Currency();

        currency.setId(1L);
        currency.setCode("USD");
        currency.setRate(1.0);

        assertEquals(1L, currency.getId());
        assertEquals("USD", currency.getCode());
        assertEquals(1.0, currency.getRate());
    }
}
