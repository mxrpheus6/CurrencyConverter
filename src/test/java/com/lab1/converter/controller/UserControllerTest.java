package com.lab1.converter.controller;

import com.lab1.converter.cache.UserCache;
import com.lab1.converter.dao.UserRepository;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserCache userCache;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = List.of(mock(UserDTO.class));

        when(userService.getAllUsers()).thenReturn(users);

        assertEquals(new ResponseEntity<>(users, HttpStatus.OK), userController.getAllUsers());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Long id = 1L;
        UserDTO user = mock(UserDTO.class);

        when(userService.getUserById(id)).thenReturn(user);

        assertEquals(ResponseEntity.ok(user), userController.getUserById(id));

        verify(userService, times(1)).getUserById(id);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(1L);

        UserDTO createdUser = new UserDTO();

        when(userService.createUser(user)).thenReturn(createdUser);

        ResponseEntity<UserDTO> expectedResponse = new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        ResponseEntity<UserDTO> actualResponse = userController.createUser(user);

        assertEquals(expectedResponse, actualResponse);

        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testCreateUsers() {
        UserController userController = new UserController(userService, userCache);

        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        List<UserDTO> createdUserDTOList = new ArrayList<>();

        when(userService.createUsers(userList)).thenReturn(createdUserDTOList);
        ResponseEntity<List<UserDTO>> responseEntity = userController.createUsers(createdUserDTOList);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdUserDTOList.size(), responseEntity.getBody().size());
    }

    @Test
    void testUpdateUserParameters() {
        UserController userController = new UserController(userService, userCache);

        Long id = 1L;

        User user = mock(User.class);
        UserDTO userDTO = mock(UserDTO.class);

        when(userService.updateUser(id, user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expectedResponse = new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        ResponseEntity<UserDTO> actualResponse = userController.updateUser(id, user);

        assertEquals(expectedResponse, actualResponse);

        verify(userService, times(1)).updateUser(id, user);
    }

    @Test
    void testDeleteUser() {
        UserController userController = new UserController(userService, userCache);
        Long id = 1L;

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<String> actualResponse = userController.deleteUser(id);

        assertEquals(expectedResponse, actualResponse);

        verify(userService, times(1)).deleteUser(id);
    }
}
