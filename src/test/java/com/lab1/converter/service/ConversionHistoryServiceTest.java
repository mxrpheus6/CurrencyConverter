package com.lab1.converter.service;

import com.lab1.converter.dao.ConversionHistoryRepository;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ConversionHistoryServiceTest {

    @Mock
    private ConversionHistoryRepository conversionHistoryRepository;

    @Mock
    private UserService userService;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private ConversionHistoryService conversionHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetConversionById() {
        Long id = 1L;
        ConversionHistory conversion = new ConversionHistory();
        conversion.setId(1L);
        conversion.setFromCurrency("USD");
        conversion.setAmount(1000.0);
        conversion.setToCurrency("BYN");
        conversion.setConvertedAmount(3250.0);

        when(conversionHistoryRepository.findById(id)).thenReturn(Optional.of(conversion));

        ConversionHistoryDTO conversionDTO = conversionHistoryService.getConversionById(id);

        assertEquals(conversion.getId(), conversionDTO.getId());
        assertEquals(conversion.getFromCurrency(), conversionDTO.getFromCurrency());
        assertEquals(conversion.getAmount(), conversionDTO.getAmount());
        assertEquals(conversion.getToCurrency(), conversionDTO.getToCurrency());
        assertEquals(conversion.getConvertedAmount(), conversionDTO.getConvertedAmount());
    }

    @Test
    void testGetConversionById_ConversionNotFound() {
        Long id = 1L;

        when(conversionHistoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ConversionNotFoundException.class, () -> conversionHistoryService.getConversionById(id));
    }
}
