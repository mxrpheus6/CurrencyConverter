package com.lab1.converter.cache;

import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConversionHistoryCacheTest {
    private ConversionHistoryCache conversionHistoryCache;

    @BeforeEach
    void setUp() {
        conversionHistoryCache = new ConversionHistoryCache();
    }

    @Test
    void testPutAndGet() {
        int key = 1;
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryCache.put(key, conversionHistoryDTO);
        ConversionHistoryDTO retrievedConversionHistoryCache = conversionHistoryCache.get(key);

        assertEquals(conversionHistoryDTO, retrievedConversionHistoryCache);
    }

    @Test
    void testGetNoneExistKey() {
        int key = 2;

        ConversionHistoryDTO retrievedConversionHistoryCache = conversionHistoryCache.get(key);

        assertNull(retrievedConversionHistoryCache);
    }

    @Test
    void testClear() {
        int key = 1;
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryCache.put(key, conversionHistoryDTO);
        conversionHistoryCache.clear();
        ConversionHistoryDTO retrievedConversionHistoryCache = conversionHistoryCache.get(key);

        assertNull(retrievedConversionHistoryCache);
    }
}
