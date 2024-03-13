package com.lab1.converter.dto;

import com.lab1.converter.entity.User;

public class UserBaseDTO {
    private Long id;
    private String name;
    private String email;

    public static UserBaseDTO toModel(User user) {
        UserBaseDTO model = new UserBaseDTO();

        model.setId(user.getId());
        model.setName(user.getName());
        model.setEmail(user.getEmail());

        return model;
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
}
