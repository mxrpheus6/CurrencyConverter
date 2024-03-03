package com.lab1.converter.service;

import com.lab1.converter.dao.ConversionRepository;
import com.lab1.converter.dao.UserRepository;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.User;
import com.lab1.converter.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(ConversionRepository conversionRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(User user) {
        return UserDTO.toModel(userRepository.save(user));
    }

    public Iterable<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDTOList.add(UserDTO.toModel(user)));
        return userDTOList;
    }

    @Transactional
    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.toModel(user);
    }

    public UserDTO updateUser(Long id, User updatedUser) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        updatedUser.setId(id);
        return UserDTO.toModel(userRepository.save(updatedUser));
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
