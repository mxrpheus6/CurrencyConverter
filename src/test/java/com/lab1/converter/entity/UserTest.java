package com.lab1.converter.entity;

import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();

        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Naruto");

        assertEquals(1L, user.getId());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("Naruto", user.getName());
    }

    @Test
    void testConversionHistoryAssociation() {
        User user = new User();
        ConversionHistory conversion1 = new ConversionHistory();
        ConversionHistory conversion2 = new ConversionHistory();

        List<ConversionHistory> conversions = new ArrayList<>();
        conversions.add(conversion1);
        conversions.add(conversion2);

        user.setConversions(conversions);

        assertEquals(2, user.getConversions().size());
        assertTrue(user.getConversions().contains(conversion1));
        assertTrue(user.getConversions().contains(conversion2));
    }
}
