package com.lab1.conerter.cache;

import com.lab1.converter.cache.UserCache;
import com.lab1.converter.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void testClear() {
        int key = 1;
        UserDTO userDTO = new UserDTO();

        userCache.put(key, userDTO);
        userCache.clear();
        UserDTO retrievedUserDTO = userCache.get(key);

        assertNull(retrievedUserDTO);
    }
}
