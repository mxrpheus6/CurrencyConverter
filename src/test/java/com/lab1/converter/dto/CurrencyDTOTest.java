package com.lab1.converter.dto;

import com.lab1.converter.entity.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyDTOTest {
    @Test
    void testCurrencyDTOTestGettersAndSetters() {
        CurrencyDTO currencyDTO = new CurrencyDTO();

        currencyDTO.setId(1L);
        currencyDTO.setCode("USD");
        currencyDTO.setRate(1.0);

        assertEquals(1L, currencyDTO.getId());
        assertEquals("USD", currencyDTO.getCode());
        assertEquals(1.0, currencyDTO.getRate());
    }

    @Test
    void testToModel() {
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("USD");
        currency.setRate(1.0);

        CurrencyDTO currencyDTO = CurrencyDTO.toModel(currency);

        assertEquals(1L, currencyDTO.getId());
        assertEquals("USD", currencyDTO.getCode());
        assertEquals(1.0, currencyDTO.getRate());
    }
}
