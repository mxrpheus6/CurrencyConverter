package com.lab1.converter.dto;

import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    private List<ConversionHistoryBaseDTO> conversions = new ArrayList<>();

    public static UserDTO toModel(User user) {
        UserDTO model = new UserDTO();

        model.setId(user.getId());
        model.setName(user.getName());
        model.setEmail(user.getEmail());

        List<ConversionHistory> conversionsCopy = user.getConversions();
        List<ConversionHistoryBaseDTO> conversionsCopyBaseDTO = new ArrayList<>();

        if (conversionsCopy != null) {
            for (ConversionHistory conversion: conversionsCopy) {
                 conversionsCopyBaseDTO.add(ConversionHistoryBaseDTO.toModel(conversion));
            }
        }

        model.setConversions(conversionsCopyBaseDTO);

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

    public List<ConversionHistoryBaseDTO> getConversions() {
        return conversions;
    }

    public void setConversions(List<ConversionHistoryBaseDTO> conversions) {
        this.conversions = conversions;
    }
}
