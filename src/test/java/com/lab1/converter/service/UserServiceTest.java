package com.lab1.converter.service;

import com.lab1.converter.dao.UserRepository;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setName("Pablo");
        user.setEmail("golf@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUserById(id);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();

        user1.setId(1L);
        user2.setId(2L);

        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> foundUserList = userService.getAllUsers();

        assertEquals(userList.size(), foundUserList.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), foundUserList.get(i).getId());
        }
    }

    @Test
    void testGetUserById_UserNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    void testCreateUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setName("Pablo");
        user.setEmail("golf@example.com");

        when(userRepository.save(any())).thenReturn(user);

        UserDTO createdUser = userService.createUser(user);

        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(any())).thenReturn(user);

        UserDTO updatedUser = userService.updateUser(id, user);

        assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long id = 1L;
        User user = new User();

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, user));
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
    }
}
