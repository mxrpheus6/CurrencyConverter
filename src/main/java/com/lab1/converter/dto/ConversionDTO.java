package com.lab1.converter.dto;

import com.lab1.converter.entity.Conversion;

public class ConversionDTO {
    private Long id;
    private UserDTO user;
    private String fromCurrency;
    private double amount;
    private String toCurrency;
    private double convertedAmount;

    public static ConversionDTO toModel(Conversion conversion) {
        ConversionDTO model = new ConversionDTO();

        model.setId(conversion.getId());
        model.setUser(UserDTO.toModel(conversion.getUser()));
        model.setFromCurrency(conversion.getFromCurrency());
        model.setAmount(conversion.getAmount());
        model.setToCurrency(conversion.getToCurrency());
        model.setConvertedAmount(conversion.getConvertedAmount());

        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
