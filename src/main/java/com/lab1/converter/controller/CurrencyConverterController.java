package com.lab1.converter.controller;

import com.lab1.converter.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;

    @Autowired
    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    public static class ConversionRestResponse {
        private String fromCurrency;
        private double amount;
        private String toCurrency;
        private double convertedAmount;

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

    @GetMapping("/convertor/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public ConversionRestResponse restConvert(@PathVariable String fromCurrency,
                                              @PathVariable double amount,
                                              @PathVariable String toCurrency) {
        ConversionRestResponse result = new ConversionRestResponse();
        result.setFromCurrency(fromCurrency.toUpperCase());
        result.setAmount(amount);
        result.setToCurrency(toCurrency.toUpperCase());
        result.setConvertedAmount(currencyConverterService.convertCurrency(fromCurrency, amount, toCurrency));

        return result;
    }
}