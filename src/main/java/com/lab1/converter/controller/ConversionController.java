package com.lab1.converter.controller;

import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.entity.Currency;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import com.lab1.converter.exceptions.CurrencyNotFoundException;
import com.lab1.converter.exceptions.UserNotFoundException;
import com.lab1.converter.service.CurrencyService;
import com.lab1.converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lab1.converter.service.ConversionHistoryService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/conversions")
public class ConversionController {

    private final ConversionHistoryService conversionHistoryService;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Autowired
    public ConversionController(ConversionHistoryService currencyConverterService, CurrencyService currencyService, UserService userService) {
        this.conversionHistoryService = currencyConverterService;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @PostMapping("/create")
    public ConversionHistoryDTO createConversion(@RequestBody ConversionHistory conversionHistory)  {
        return conversionHistoryService.createConversion(conversionHistory);
    }

    @GetMapping
    public Iterable<ConversionHistoryDTO> getAllConversions() {
        return conversionHistoryService.getAllConversions();
    }

    @GetMapping("/{id}")
    public ConversionHistoryDTO getConversionById(@PathVariable Long id) throws ConversionNotFoundException {
        return conversionHistoryService.getConversionById(id);
    }

    @PutMapping("/update/{id}")
    public ConversionHistoryDTO updateConversion(@PathVariable Long id, @RequestBody ConversionHistory conversionHistory) throws ConversionNotFoundException {
        return conversionHistoryService.updateConversion(id, conversionHistory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConversion(@PathVariable Long id) throws ConversionNotFoundException {
        conversionHistoryService.deleteConversion(id);
    }

    @GetMapping("/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public Map<String, Object> restConvert(@PathVariable Long userId,
                                           @PathVariable String fromCurrency,
                                           @PathVariable double amount,
                                           @PathVariable String toCurrency) throws UserNotFoundException, CurrencyNotFoundException {

        Currency from = currencyService.getCurrencyByCode(fromCurrency.toUpperCase());
        Currency to = currencyService.getCurrencyByCode(toCurrency.toUpperCase());

        if (from == null || to == null) {
            throw new CurrencyNotFoundException("Currency Not Found");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", userId);
        result.put("fromCurrency", fromCurrency.toUpperCase());
        result.put("amount", amount);
        result.put("toCurrency", toCurrency.toUpperCase());
        result.put("convertedAmount", conversionHistoryService.convertCurrency(userId, fromCurrency, amount, toCurrency));

        return result;
    }
}