package com.lab1.converter.controller;

import com.lab1.converter.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
        currencyService.fetchAndSaveCurrencyRates();
        log.info("Currency rates are updated");
    }

    @GetMapping("/update-rates")
    public ResponseEntity<String> updateCurrencyRates() {
        log.info("GET endpoint /currency/update-rates was called");
        currencyService.fetchAndSaveCurrencyRates();
        return ResponseEntity.ok("Currency rates updated successfully!");
    }
}