package com.lab1.converter.dto;

import com.lab1.converter.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDTOTest {

    @Test
    void testUserDTOGettersAndSetters() {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(1L);
        userDTO.setEmail("test@gmail.com");
        userDTO.setName("Naruto");

        assertEquals(1L, userDTO.getId());
        assertEquals("test@gmail.com", userDTO.getEmail());
        assertEquals("Naruto", userDTO.getName());
    }

    @Test
    void testToModel() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Naruto");

        UserDTO userDTO = UserDTO.toModel(user);

        assertEquals(1L, userDTO.getId());
        assertEquals("test@gmail.com", userDTO.getEmail());
        assertEquals("Naruto", userDTO.getName());
    }

    @Test
    void testToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@gmail.com");
        userDTO.setName("Naruto");

        User user = UserDTO.toEntity(userDTO);

        assertEquals(1L, user.getId());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("Naruto", user.getName());
    }

    @Test
    void testConversionBaseDTOAssociation() {
        List<ConversionHistoryBaseDTO> conversionsDTO = new ArrayList<>();
        ConversionHistoryBaseDTO conversionDTO1 = new ConversionHistoryBaseDTO();
        ConversionHistoryBaseDTO conversionDTO2 = new ConversionHistoryBaseDTO();
        conversionsDTO.add(conversionDTO1);
        conversionsDTO.add(conversionDTO2);

        UserDTO userDTO = new UserDTO();
        userDTO.setConversions(conversionsDTO);

        assertEquals(2, userDTO.getConversions().size());
        assertTrue(userDTO.getConversions().contains(conversionDTO1));
        assertTrue(userDTO.getConversions().contains(conversionDTO2));
    }
}
