package com.lab1.converter.controller;

import com.lab1.converter.cache.UserCache;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.exceptions.UserNotFoundException;
import com.lab1.converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserCache userCache;

    @Autowired
    public UserController(UserService userService, UserCache userCache) {
        this.userService = userService;
        this.userCache = userCache;
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody User user) {
        UserDTO createdUser = userService.createUser(user);
        userCache.put(createdUser.getId().intValue(), createdUser);
        return createdUser;
    }

    @GetMapping
    public Iterable<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws UserNotFoundException {
        if (userCache.contains(id.intValue())) {
            return userCache.get(id.intValue());
        } else {
            UserDTO userDTO = userService.getUserById(id);
            userCache.put(userDTO.getId().intValue(), userDTO);
            return userDTO;
        }
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody User updatedUser) throws UserNotFoundException {
        UserDTO updatedUserDTO = userService.updateUser(id, updatedUser);
        userCache.put(updatedUserDTO.getId().intValue(), updatedUserDTO);
        return updatedUserDTO;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        userCache.remove(id.intValue());
    }

}
