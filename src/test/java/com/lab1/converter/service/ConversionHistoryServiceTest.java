package com.lab1.converter.service;

import com.lab1.converter.dao.ConversionHistoryRepository;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.entity.User;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(1L);
        conversionHistory.setFromCurrency("USD");
        conversionHistory.setAmount(1000.0);
        conversionHistory.setToCurrency("BYN");
        conversionHistory.setConvertedAmount(3250.0);

        when(conversionHistoryRepository.findById(id)).thenReturn(Optional.of(conversionHistory));

        ConversionHistoryDTO conversionDTO = conversionHistoryService.getConversionById(id);

        assertEquals(conversionHistory.getId(), conversionDTO.getId());
        assertEquals(conversionHistory.getFromCurrency(), conversionDTO.getFromCurrency());
        assertEquals(conversionHistory.getAmount(), conversionDTO.getAmount());
        assertEquals(conversionHistory.getToCurrency(), conversionDTO.getToCurrency());
        assertEquals(conversionHistory.getConvertedAmount(), conversionDTO.getConvertedAmount());
    }

    @Test
    void testGetConversionById_ConversionNotFound() {
        Long id = 1L;

        when(conversionHistoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ConversionNotFoundException.class, () -> conversionHistoryService.getConversionById(id));
    }

    @Test
    void testGetAllConversions() {
        List<ConversionHistory> conversionHistoryList = new ArrayList<>();
        ConversionHistory conversionHistory1 = new ConversionHistory();
        ConversionHistory conversionHistory2 = new ConversionHistory();

        conversionHistory1.setId(1L);
        conversionHistory2.setId(2L);

        conversionHistoryList.add(conversionHistory1);
        conversionHistoryList.add(conversionHistory2);

        when(conversionHistoryRepository.findAll()).thenReturn(conversionHistoryList);

        List<ConversionHistoryDTO> foundConversionHistoryList = conversionHistoryService.getAllConversions();

        assertEquals(conversionHistoryList.size(), foundConversionHistoryList.size());
        for (int i = 0; i < conversionHistoryList.size(); i++) {
            assertEquals(conversionHistoryList.get(i).getId(), foundConversionHistoryList.get(i).getId());
        }
    }

    @Test
    void testCreateConversion() {
        Long id = 1L;
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(1L);
        conversionHistory.setFromCurrency("USD");
        conversionHistory.setAmount(1000.0);
        conversionHistory.setToCurrency("BYN");
        conversionHistory.setConvertedAmount(3250.0);

        when(conversionHistoryRepository.save(any())).thenReturn(conversionHistory);

        ConversionHistoryDTO createdConversion = conversionHistoryService.createConversion(conversionHistory);

        assertEquals(conversionHistory.getId(), createdConversion.getId());
        assertEquals(conversionHistory.getFromCurrency(), createdConversion.getFromCurrency());
        assertEquals(conversionHistory.getAmount(), createdConversion.getAmount());
        assertEquals(conversionHistory.getToCurrency(), createdConversion.getToCurrency());
        assertEquals(conversionHistory.getConvertedAmount(), createdConversion.getConvertedAmount());
    }

    @Test
    void testUpdateConversion() {
        Long id = 1L;
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(id);

        when(conversionHistoryRepository.existsById(id)).thenReturn(true);
        when(conversionHistoryRepository.save(any())).thenReturn(conversionHistory);

        ConversionHistoryDTO updatedConversion = conversionHistoryService.updateConversion(id, conversionHistory);

        assertEquals(conversionHistory.getId(), updatedConversion.getId());
    }

    @Test
    void testUpdateConversion_ConversionNotFound() {
        Long id = 1L;
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(id);

        when(conversionHistoryRepository.existsById(id)).thenReturn(false);

        assertThrows(ConversionNotFoundException.class, () -> conversionHistoryService.updateConversion(id, conversionHistory));
    }

    @Test
    void testDeleteConversion() {
        Long id = 1L;

        when(conversionHistoryRepository.existsById(id)).thenReturn(true);

        conversionHistoryService.deleteConversion(id);

        verify(conversionHistoryRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteConversion_ConversionNotFound() {
        Long id = 1L;

        when(conversionHistoryRepository.existsById(id)).thenReturn(false);

        assertThrows(ConversionNotFoundException.class, () -> conversionHistoryService.deleteConversion(id));
    }
}
