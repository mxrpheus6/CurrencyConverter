package com.lab1.converter.cache;

import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testContains() {
        int key = 1;
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryCache.put(key, conversionHistoryDTO);
        boolean contains = conversionHistoryCache.contains(key);

        assertTrue(contains);
    }

    @Test
    void testSize() {
        int key = 1;
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryCache.put(key, conversionHistoryDTO);
        int size = conversionHistoryCache.size();

        assertEquals(1, size);
    }

    @Test
    void testRemove() {
        int key = 1;
        ConversionHistoryDTO conversionHistoryDTO = new ConversionHistoryDTO();

        conversionHistoryCache.put(key, conversionHistoryDTO);
        conversionHistoryCache.remove(key);

        ConversionHistoryDTO retrievedConversionHistoryDTO = conversionHistoryCache.get(key);

        assertNull(retrievedConversionHistoryDTO);
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
