package com.lab1.converter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lab1.converter.entity.Conversion;
import com.lab1.converter.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    @JsonIgnoreProperties("user")
    private List<Conversion> conversions = new ArrayList<>();

    public static UserDTO toModel(User user) {
        UserDTO model = new UserDTO();

        model.setId(user.getId());
        model.setName(user.getName());
        model.setEmail(user.getEmail());

        if (user.getConversions() != null) {
            List<Conversion> conversionCopy = new ArrayList<>(user.getConversions());
            model.setConversions(conversionCopy);
        }

        return model;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(List<Conversion> conversions) {
        this.conversions = conversions;
    }
}
