package com.lab1.converter.dto;

import com.lab1.converter.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBaseDTOTest {

    @Test
    void testUserBaseDTOGettersAndSetters() {
        UserBaseDTO userBaseDTO = new UserBaseDTO();

        userBaseDTO.setId(1L);
        userBaseDTO.setEmail("test@gmail.com");
        userBaseDTO.setName("Naruto");

        assertEquals(1L, userBaseDTO.getId());
        assertEquals("test@gmail.com", userBaseDTO.getEmail());
        assertEquals("Naruto", userBaseDTO.getName());
    }

    @Test
    void testToModel() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("Naruto");

        UserBaseDTO userBaseDTO = UserBaseDTO.toModel(user);

        assertEquals(1L, userBaseDTO.getId());
        assertEquals("test@gmail.com", userBaseDTO.getEmail());
        assertEquals("Naruto", userBaseDTO.getName());
    }

}
