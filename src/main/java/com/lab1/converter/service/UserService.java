package com.lab1.converter.service;

import com.lab1.converter.dao.UserRepository;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(User user) {
        return UserDTO.toModel(userRepository.save(user));
    }

    public List<UserDTO> createUsers(List<User> users) {
        Iterable<User> savedUsersIterable = userRepository.saveAll(users);
        List<UserDTO> userDTOList = new ArrayList<>();

        StreamSupport.stream(savedUsersIterable.spliterator(), false)
                .forEach(user -> userDTOList.add(UserDTO.toModel(user)));

        return userDTOList;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDTOList.add(UserDTO.toModel(user)));
        return userDTOList;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserDTO.toModel(user);
    }

    public UserDTO updateUser(Long id, User updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        updatedUser.setId(id);
        return UserDTO.toModel(userRepository.save(updatedUser));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}
