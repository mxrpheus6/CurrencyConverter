package com.lab1.converter.dto;

import com.lab1.converter.entity.ConversionHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionHistoryDTOTest {

    @Test
    void testConversionHistoryDTOGettersAndSetters() {
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryDTO.setId(1L);
        conversionHistoryDTO.setFromCurrency("USD");
        conversionHistoryDTO.setAmount(1000.0);
        conversionHistoryDTO.setToCurrency("BYN");
        conversionHistoryDTO.setConvertedAmount(3250.0);

        assertEquals(1L, conversionHistoryDTO.getId());
        assertEquals("USD", conversionHistoryDTO.getFromCurrency());
        assertEquals(1000.0, conversionHistoryDTO.getAmount());
        assertEquals("BYN", conversionHistoryDTO.getToCurrency());
        assertEquals(3250.0, conversionHistoryDTO.getConvertedAmount());
    }

    @Test
    void testToModel() {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(1L);
        conversionHistory.setFromCurrency("USD");
        conversionHistory.setAmount(1000.0);
        conversionHistory.setToCurrency("BYN");
        conversionHistory.setConvertedAmount(3250.0);

        ConversionHistoryDTO conversionHistoryDTO = ConversionHistoryDTO.toModel(conversionHistory);

        assertEquals(1L, conversionHistoryDTO.getId());
        assertEquals("USD", conversionHistoryDTO.getFromCurrency());
        assertEquals(1000.0, conversionHistoryDTO.getAmount());
        assertEquals("BYN", conversionHistoryDTO.getToCurrency());
        assertEquals(3250.0, conversionHistoryDTO.getConvertedAmount());
    }

    @Test
    void testUserBaseDTOAssociation() {
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        UserBaseDTO userBaseDTO = new UserBaseDTO();
        conversionHistoryDTO.setUser(userBaseDTO);

        assertEquals(userBaseDTO, conversionHistoryDTO.getUser());
    }

}
