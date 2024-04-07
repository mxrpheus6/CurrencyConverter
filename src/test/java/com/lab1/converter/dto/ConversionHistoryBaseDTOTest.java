package com.lab1.converter.dto;

import com.lab1.converter.entity.ConversionHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionHistoryBaseDTOTest {
    @Test
    void testConversionHistoryBaseDTOGettersAndSetters() {
        ConversionHistoryBaseDTO conversionHistoryBaseDTO = new ConversionHistoryBaseDTO();

        conversionHistoryBaseDTO.setId(1L);
        conversionHistoryBaseDTO.setFromCurrency("USD");
        conversionHistoryBaseDTO.setAmount(1000.0);
        conversionHistoryBaseDTO.setToCurrency("BYN");
        conversionHistoryBaseDTO.setConvertedAmount(3250.0);
        conversionHistoryBaseDTO.setDate("2024");

        assertEquals(1L, conversionHistoryBaseDTO.getId());
        assertEquals("USD", conversionHistoryBaseDTO.getFromCurrency());
        assertEquals(1000.0, conversionHistoryBaseDTO.getAmount());
        assertEquals("BYN", conversionHistoryBaseDTO.getToCurrency());
        assertEquals(3250.0, conversionHistoryBaseDTO.getConvertedAmount());
        assertEquals("2024", conversionHistoryBaseDTO.getDate());
    }

    @Test
    void testToModel() {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(1L);
        conversionHistory.setFromCurrency("USD");
        conversionHistory.setAmount(1000.0);
        conversionHistory.setToCurrency("BYN");
        conversionHistory.setConvertedAmount(3250.0);
        conversionHistory.setDate("2024");

        ConversionHistoryBaseDTO conversionHistoryBaseDTO = ConversionHistoryBaseDTO.toModel(conversionHistory);

        assertEquals(1L, conversionHistoryBaseDTO.getId());
        assertEquals("USD", conversionHistoryBaseDTO.getFromCurrency());
        assertEquals(1000.0, conversionHistoryBaseDTO.getAmount());
        assertEquals("BYN", conversionHistoryBaseDTO.getToCurrency());
        assertEquals(3250.0, conversionHistoryBaseDTO.getConvertedAmount());
        assertEquals("2024", conversionHistory.getDate());
    }
}
