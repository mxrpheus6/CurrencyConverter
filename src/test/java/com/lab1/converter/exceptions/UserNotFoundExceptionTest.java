package com.lab1.converter.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserNotFoundExceptionTest {

    @Test
    void testConstructor() {
        Long userId = 123L;

        UserNotFoundException e = new UserNotFoundException(userId);

        assertEquals("User with id=" + userId + " not found", e.getMessage());
    }
}