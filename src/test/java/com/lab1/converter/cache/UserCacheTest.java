package com.lab1.converter.cache;

import com.lab1.converter.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCacheTest {
    private UserCache userCache;

    @BeforeEach
    void setUp() {
        userCache = new UserCache();
    }

    @Test
    void testPutAndGet() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        UserDTO retrievedUserDTO = userCache.get(key);

        assertEquals(userDTO, retrievedUserDTO);
    }

    @Test
    void testGetNoneExistKey() {
        int key = 2;

        UserDTO  retrievedUserDTO = userCache.get(key);

        assertNull(retrievedUserDTO);
    }

    @Test
    void testContains() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        boolean contains = userCache.contains(key);

        assertTrue(contains);
    }

    @Test
    void testSize() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        int size = userCache.size();

        assertEquals(1, size);
    }

    @Test
    void testRemove() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        userCache.remove(key);

        UserDTO retrievedUserDTO = userCache.get(key);

        assertNull(retrievedUserDTO);
    }

    @Test
    void testClear() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        userCache.clear();
        UserDTO retrievedUserDTO = userCache.get(key);

        assertNull(retrievedUserDTO);
    }
}
