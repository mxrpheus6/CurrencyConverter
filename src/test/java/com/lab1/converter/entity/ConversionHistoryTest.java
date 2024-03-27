package com.lab1.converter.entity;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionHistoryTest {

    @Test
    void testConversionHistoryGettersAndSetters() {
        ConversionHistory conversionHistory = new ConversionHistory();

        conversionHistory.setId(1L);
        conversionHistory.setFromCurrency("USD");
        conversionHistory.setAmount(1000.0);
        conversionHistory.setToCurrency("BYN");
        conversionHistory.setConvertedAmount(3250.0);

        assertEquals(1L, conversionHistory.getId());
        assertEquals("USD", conversionHistory.getFromCurrency());
        assertEquals(1000.0, conversionHistory.getAmount());
        assertEquals("BYN", conversionHistory.getToCurrency());
        assertEquals(3250.0, conversionHistory.getConvertedAmount());
    }

    @Test
    void testUserAssociation() {
        ConversionHistory conversionHistory = new ConversionHistory();
        User user = new User();

        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Naruto");

        conversionHistory.setUser(user);

        assertEquals(user, conversionHistory.getUser());
    }
}
