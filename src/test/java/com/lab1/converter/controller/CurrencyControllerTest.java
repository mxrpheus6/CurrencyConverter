package com.lab1.converter.controller;

import com.lab1.converter.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInit() {
        currencyController.init();

        verify(currencyService).fetchAndSaveCurrencyRates();
    }

    @Test
    void testUpdateCurrencyRates() {
        ResponseEntity<String> response = currencyController.updateCurrencyRates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Currency rates updated successfully!", response.getBody());
        verify(currencyService).fetchAndSaveCurrencyRates();
    }
}
