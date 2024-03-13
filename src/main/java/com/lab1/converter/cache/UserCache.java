package com.lab1.converter.cache;

import com.lab1.converter.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCache {
    private final Map<Integer, UserDTO> userResponseMap = new ConcurrentHashMap<>();

    public void put(int key, UserDTO value) {
        userResponseMap.put(key, value);
    }

    public UserDTO get(int key) {
        return userResponseMap.get(key);
    }

    public boolean contains(int key) {
        return userResponseMap.containsKey(key);
    }

    public void remove(int key) {
        userResponseMap.remove(key);
    }

    public void clear() {
        userResponseMap.clear();
    }

    public int size() {
        return userResponseMap.size();
    }
}
