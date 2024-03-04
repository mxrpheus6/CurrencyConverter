package com.lab1.converter.controller;

import com.lab1.converter.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostConstruct
    public void init() {
        updateCurrencyRates();
    }

    @GetMapping("/update-rates")
    public String updateCurrencyRates() {
        currencyService.fetchAndSaveCurrencyRates();
        return "Currency rates updated successfully!";
    }
}