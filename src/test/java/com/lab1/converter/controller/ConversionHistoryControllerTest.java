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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
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
        List<ConversionHistoryDTO> conversionHistoryDTOList = new ArrayList<>();
        ConversionHistoryDTO conversionHistoryDTO1 = new ConversionHistoryDTO();
        conversionHistoryDTO1.setId(1L);
        ConversionHistoryDTO conversionHistoryDTO2 = new ConversionHistoryDTO();
        conversionHistoryDTO2.setId(2L);
        conversionHistoryDTOList.add(conversionHistoryDTO1);
        conversionHistoryDTOList.add(conversionHistoryDTO2);

        when(conversionHistoryService.getAllConversions()).thenReturn(conversionHistoryDTOList);

        mockMvc.perform(get("/conversions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
