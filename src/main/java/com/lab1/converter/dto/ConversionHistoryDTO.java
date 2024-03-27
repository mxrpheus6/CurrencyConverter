package com.lab1.converter.dto;

import com.lab1.converter.entity.ConversionHistory;

public class ConversionHistoryDTO {
    private Long id;

    private UserBaseDTO user;

    private String fromCurrency;
    private double amount;
    private String toCurrency;
    private double convertedAmount;

    public static ConversionHistoryDTO toModel(ConversionHistory conversionHistory) {
        ConversionHistoryDTO model = new ConversionHistoryDTO();

        model.setId(conversionHistory.getId());
        if (conversionHistory.getUser() != null) {
            model.setUser(UserBaseDTO.toModel(conversionHistory.getUser()));
        }
        model.setFromCurrency(conversionHistory.getFromCurrency());
        model.setAmount(conversionHistory.getAmount());
        model.setToCurrency(conversionHistory.getToCurrency());
        model.setConvertedAmount(conversionHistory.getConvertedAmount());

        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBaseDTO getUser() {
        return user;
    }

    public void setUser(UserBaseDTO user) {
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
