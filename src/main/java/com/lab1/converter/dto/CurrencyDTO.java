package com.lab1.converter.dto;

import com.lab1.converter.entity.Currency;

public class CurrencyDTO {
    private Long id;
    private String code;
    private double rate;

    public static CurrencyDTO toModel(Currency currency) {
        CurrencyDTO model = new CurrencyDTO();

        model.setId(currency.getId());
        model.setCode(currency.getCode());
        model.setRate(currency.getRate());

        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
