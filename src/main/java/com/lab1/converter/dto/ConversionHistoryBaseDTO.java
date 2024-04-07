package com.lab1.converter.dto;

import com.lab1.converter.entity.ConversionHistory;

public class ConversionHistoryBaseDTO {
    private Long id;
    private String fromCurrency;
    private double amount;
    private String toCurrency;
    private double convertedAmount;
    private String date;

    public static ConversionHistoryBaseDTO toModel(ConversionHistory conversionHistory) {
        ConversionHistoryBaseDTO model = new ConversionHistoryBaseDTO();

        model.setId(conversionHistory.getId());
        model.setFromCurrency(conversionHistory.getFromCurrency());
        model.setAmount(conversionHistory.getAmount());
        model.setToCurrency(conversionHistory.getToCurrency());
        model.setConvertedAmount(conversionHistory.getConvertedAmount());
        model.setDate(conversionHistory.getDate());

        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
