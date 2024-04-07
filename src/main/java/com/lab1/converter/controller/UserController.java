package com.lab1.converter.controller;

import com.lab1.converter.cache.UserCache;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.service.RequestCounterService;
import com.lab1.converter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserCache userCache;
    private final RequestCounterService requestCounterService;

    @Autowired
    public UserController(UserService userService, UserCache userCache, RequestCounterService requestCounterService) {
        this.userService = userService;
        this.userCache = userCache;
        this.requestCounterService = requestCounterService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        log.info("POST endpoint /users/create - " + requestCounterService.incrementAndGet());
        UserDTO createdUser = userService.createUser(user);
        userCache.put(user.getId().intValue(), createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/create/many")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<User> userList) {
        log.info("POST endpoint /users/create/many - " + requestCounterService.incrementAndGet());

        List<UserDTO> createdUsers = userService.createUsers(userList);
        return new ResponseEntity<>(createdUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET endpoint /users - " + requestCounterService.incrementAndGet());
        List<UserDTO> userDTOList = userService.getAllUsers();
        for (UserDTO userDTO: userDTOList) {
            userCache.put(userDTO.getId().intValue(), userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("GET endpoint /users/{id} - " + requestCounterService.incrementAndGet());
        if (userCache.contains(id.intValue())) {
            return new ResponseEntity<>(userCache.get(id.intValue()), HttpStatus.OK);
        } else {
            UserDTO userDTO = userService.getUserById(id);
            userCache.put(userDTO.getId().intValue(), userDTO);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        log.info("PUT endpoint /users/update/{id} - " + requestCounterService.incrementAndGet());
        UserDTO updatedUserDTO = userService.updateUser(id, updatedUser);
        userCache.put(updatedUserDTO.getId().intValue(), updatedUserDTO);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("DELETE endpoint /users/delete/{id} - " + requestCounterService.incrementAndGet());
        userService.deleteUser(id);
        userCache.remove(id.intValue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
