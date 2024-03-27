package com.lab1.converter.controller;

import com.lab1.converter.cache.ConversionHistoryCache;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.service.ConversionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ConversionHistoryControllerTest {
    @Mock
    private ConversionHistoryService conversionHistoryService;

    @Mock
    private ConversionHistoryCache conversionHistoryCache;

    @InjectMocks
    private ConversionHistoryController conversionHistoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(conversionHistoryController).build();
    }

    @Test
    void testGetAllConversions() throws Exception {
        List<ConversionHistoryDTO> conversions = List.of(mock(ConversionHistoryDTO.class));

        when(conversionHistoryService.getAllConversions()).thenReturn(conversions);

        assertEquals(new ResponseEntity<>(conversions, HttpStatus.OK), conversionHistoryController.getAllConversions());

        verify(conversionHistoryService, times(1)).getAllConversions();
    }
}
