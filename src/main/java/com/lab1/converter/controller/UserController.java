package com.lab1.converter.controller;

import com.lab1.converter.cache.UserCache;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        log.info("POST endpoint /users/create was called");
        UserDTO createdUser = userService.createUser(user);
        userCache.put(createdUser.getId().intValue(), createdUser);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET endpoint /users was called");
        List<UserDTO> userDTOList = userService.getAllUsers();
        for (UserDTO userDTO: userDTOList) {
            userCache.put(userDTO.getId().intValue(), userDTO);
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("GET endpoint /users/{id} was called");
        if (userCache.contains(id.intValue())) {
            return ResponseEntity.ok(userCache.get(id.intValue()));
        } else {
            UserDTO userDTO = userService.getUserById(id);
            userCache.put(userDTO.getId().intValue(), userDTO);
            return ResponseEntity.ok(userDTO);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        log.info("PUT endpoint /users/update/{id} was called");
        UserDTO updatedUserDTO = userService.updateUser(id, updatedUser);
        userCache.put(updatedUserDTO.getId().intValue(), updatedUserDTO);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("DELETE endpoint /users/delete/{id} was called");
        userService.deleteUser(id);
        userCache.remove(id.intValue());
        return ResponseEntity.ok("User with id=" + id + " was successfully deleted");
    }

}
