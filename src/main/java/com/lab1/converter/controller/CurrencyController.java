package com.lab1.converter.controller;

import com.lab1.converter.dto.CurrencyDTO;
import com.lab1.converter.service.CurrencyService;
import com.lab1.converter.service.RequestCounterService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/codes")
@CrossOrigin
public class CurrencyController {

    private final CurrencyService currencyService;
    private final RequestCounterService requestCounterService;

    @Autowired
    public CurrencyController(CurrencyService currencyService,
                              RequestCounterService requestCounterService) {
        this.currencyService = currencyService;
        this.requestCounterService = requestCounterService;
    }

    @PostConstruct
    public void init() {
        currencyService.fetchAndSaveCurrencyRates();
        log.info("Currency rates are updated");
    }

    @GetMapping("/update-rates")
    public ResponseEntity<String> updateCurrencyRates() {
        log.info("GET endpoint /codes/update-rates was called");
        currencyService.fetchAndSaveCurrencyRates();
        return ResponseEntity.ok("Currency rates updated successfully!");
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        log.info("GET endpoint /codes/currencies - " + requestCounterService.incrementAndGet());
        List<CurrencyDTO> currencyDTOList = currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencyDTOList, HttpStatus.OK);
    }
}