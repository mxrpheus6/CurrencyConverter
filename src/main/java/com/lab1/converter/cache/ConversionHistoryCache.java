package com.lab1.converter.cache;

import com.lab1.converter.dto.ConversionHistoryDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConversionHistoryCache {
    private final Map<Integer, ConversionHistoryDTO> conversionHistoryResponseMap = new ConcurrentHashMap<>();

    public void put(int key, ConversionHistoryDTO value) {
        conversionHistoryResponseMap.put(key, value);
    }

    public ConversionHistoryDTO get(int key) {
        return conversionHistoryResponseMap.get(key);
    }

    public boolean contains(int key) {
        return conversionHistoryResponseMap.containsKey(key);
    }

    public void remove(int key) {
        conversionHistoryResponseMap.remove(key);
    }

    public void clear() {
        conversionHistoryResponseMap.clear();
    }

    public int size() {
        return conversionHistoryResponseMap.size();
    }
}
