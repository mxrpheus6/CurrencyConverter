package com.lab1.converter.controller;

import com.lab1.converter.dto.ConversionDTO;
import com.lab1.converter.entity.Conversion;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import com.lab1.converter.exceptions.UserNotFoundException;
import com.lab1.converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.lab1.converter.service.ConversionService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/conversions")
public class ConversionController {

    private final ConversionService conversionService;
    private final UserService userService;

    @Autowired
    public ConversionController(ConversionService currencyConverterService, UserService userService) {
        this.conversionService = currencyConverterService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ConversionDTO createConversion(@RequestBody Conversion conversion)  {
        return conversionService.createConversion(conversion);
    }

    @GetMapping
    public Iterable<ConversionDTO> getAllConversions() {
        return conversionService.getAllConversions();
    }

    @GetMapping("/{id}")
    public ConversionDTO getConversionById(@PathVariable Long id) throws ConversionNotFoundException {
        return conversionService.getConversionById(id);
    }

    @PutMapping("/update/{id}")
    public ConversionDTO updateConversion(@PathVariable Long id, @RequestBody Conversion conversion) throws ConversionNotFoundException {
        return conversionService.updateConversion(id, conversion);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConversion(@PathVariable Long id) throws ConversionNotFoundException {
        conversionService.deleteConversion(id);
    }

    @GetMapping("/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public Map<String, Object> restConvert(@PathVariable Long userId,
                                           @PathVariable String fromCurrency,
                                           @PathVariable double amount,
                                           @PathVariable String toCurrency) throws UserNotFoundException {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", userId);
        result.put("fromCurrency", fromCurrency.toUpperCase());
        result.put("amount", amount);
        result.put("toCurrency", toCurrency.toUpperCase());
        result.put("convertedAmount", conversionService.convertCurrency(userId, fromCurrency, amount, toCurrency));

        return result;
    }
}