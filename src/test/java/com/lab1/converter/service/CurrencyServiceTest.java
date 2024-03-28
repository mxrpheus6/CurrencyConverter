package com.lab1.converter.service;

import com.lab1.converter.dao.CurrencyRepository;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCurrencyByCode() {
        String code = "USD";
        double rate = 1.0;

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setRate(rate);

        when(currencyRepository.existsByCodeIgnoreCase(code)).thenReturn(true);
        when(currencyRepository.findByCodeIgnoreCase(code)).thenReturn(currency);

        Currency result = currencyService.getCurrencyByCode(code);

        assertNotNull(result);
        assertEquals(code, result.getCode());
        verify(currencyRepository, times(1)).existsByCodeIgnoreCase(code);
        verify(currencyRepository, times(1)).findByCodeIgnoreCase(code);
    }
}
