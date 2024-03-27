package com.lab1.converter.controller;

import com.lab1.converter.cache.ConversionHistoryCache;
import com.lab1.converter.dao.ConversionHistoryRepository;
import com.lab1.converter.dto.ConversionHistoryBaseDTO;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.service.ConversionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConversionHistoryControllerTest {
    @Mock
    private ConversionHistoryService conversionHistoryService;

    @Mock
    private ConversionHistoryRepository conversionHistoryRepository;

    @Mock
    private ConversionHistoryCache conversionHistoryCache;

    @InjectMocks
    private ConversionHistoryController conversionHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllConversions() throws Exception {
        List<ConversionHistoryDTO> conversions = List.of(mock(ConversionHistoryDTO.class));

        when(conversionHistoryService.getAllConversions()).thenReturn(conversions);

        assertEquals(new ResponseEntity<>(conversions, HttpStatus.OK), conversionHistoryController.getAllConversions());

        verify(conversionHistoryService, times(1)).getAllConversions();
    }

    @Test
    void testGetConversionsByUserId() {
        Long userId = 1L;
        List<ConversionHistoryBaseDTO> conversions = List.of(mock(ConversionHistoryBaseDTO.class));

        when(conversionHistoryService.getConversionsByUserId(userId)).thenReturn(conversions);

        assertEquals(ResponseEntity.ok(conversions), conversionHistoryController.getConversionsByUserId(userId));

        verify(conversionHistoryService, times(1)).getConversionsByUserId(userId);
    }

    @Test
    void testGetConversionById() {
        Long id = 1L;
        ConversionHistoryDTO conversion = mock(ConversionHistoryDTO.class);

        when(conversionHistoryService.getConversionById(id)).thenReturn(conversion);

        assertEquals(ResponseEntity.ok(conversion), conversionHistoryController.getConversionById(id));

        verify(conversionHistoryService, times(1)).getConversionById(id);
    }

    @Test
    void testCreateConversion() {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setId(1L);

        ConversionHistoryDTO createdConversion = new ConversionHistoryDTO();

        when(conversionHistoryService.createConversion(conversionHistory)).thenReturn(createdConversion);

        ResponseEntity<ConversionHistoryDTO> expectedResponse = new ResponseEntity<>(createdConversion, HttpStatus.CREATED);
        ResponseEntity<ConversionHistoryDTO> actualResponse = conversionHistoryController.createConversion(conversionHistory);

        assertEquals(expectedResponse, actualResponse);

        verify(conversionHistoryService, times(1)).createConversion(conversionHistory);
    }

    @Test
    void testUpdateCountryParameters() {
        ConversionHistoryController conversionHistoryController = new ConversionHistoryController(conversionHistoryService, conversionHistoryCache);

        Long id = 1L;

        ConversionHistory conversionHistory = mock(ConversionHistory.class);
        ConversionHistoryDTO conversionHistoryDTO = mock(ConversionHistoryDTO.class);

        when(conversionHistoryService.updateConversion(id, conversionHistory)).thenReturn(conversionHistoryDTO);

        ResponseEntity<ConversionHistoryDTO> expectedResponse = new ResponseEntity<>(conversionHistoryDTO, HttpStatus.CREATED);
        ResponseEntity<ConversionHistoryDTO> actualResponse = conversionHistoryController.updateConversion(id, conversionHistory);

        assertEquals(expectedResponse, actualResponse);

        verify(conversionHistoryService, times(1)).updateConversion(id, conversionHistory);
    }

    @Test
    void testDeleteCountryParameters() {
        ConversionHistoryController conversionHistoryController = new ConversionHistoryController(conversionHistoryService,
                                                                                                  conversionHistoryCache);
        Long id = 1L;

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<String> actualResponse = conversionHistoryController.deleteConversion(id);

        assertEquals(expectedResponse, actualResponse);

        verify(conversionHistoryService, times(1)).deleteConversion(id);
    }
}
