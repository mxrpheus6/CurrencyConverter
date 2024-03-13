package com.lab1.converter.controller;

import com.lab1.converter.cache.ConversionHistoryCache;
import com.lab1.converter.dto.ConversionHistoryBaseDTO;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conversions")
public class ConversionHistoryController {

    private final ConversionHistoryService conversionHistoryService;
    private final ConversionHistoryCache conversionHistoryCache;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Autowired
    public ConversionHistoryController(ConversionHistoryService currencyConverterService,
                                       ConversionHistoryCache conversionHistoryCache,
                                       CurrencyService currencyService,
                                       UserService userService) {
        this.conversionHistoryService = currencyConverterService;
        this.conversionHistoryCache = conversionHistoryCache;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @PostMapping("/create")
    public ConversionHistoryDTO createConversion(@RequestBody ConversionHistory conversionHistory)  {
        ConversionHistoryDTO createdConversion = conversionHistoryService.createConversion(conversionHistory);
        conversionHistoryCache.put(conversionHistory.getId().intValue(), createdConversion);
        return conversionHistoryService.createConversion(conversionHistory);
    }

    @GetMapping
    public Iterable<ConversionHistoryDTO> getAllConversions() {
        return conversionHistoryService.getAllConversions();
    }

    @GetMapping("useful/{userId}")
    public List<ConversionHistoryBaseDTO> getConversionsByUserId(@PathVariable Long userId) {
        return conversionHistoryService.getConversionsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ConversionHistoryDTO getConversionById(@PathVariable Long id) throws ConversionNotFoundException {
        if (conversionHistoryCache.contains(id.intValue())) {
            return conversionHistoryCache.get(id.intValue());
        } else {
            ConversionHistoryDTO conversionDTO = conversionHistoryService.getConversionById(id);
            conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
            return conversionDTO;
        }
    }

    @PutMapping("/update/{id}")
    public ConversionHistoryDTO updateConversion(@PathVariable Long id, @RequestBody ConversionHistory conversionHistory) throws ConversionNotFoundException {
        ConversionHistoryDTO updatedConversionDTO = conversionHistoryService.updateConversion(id, conversionHistory);
        conversionHistoryCache.put(updatedConversionDTO.getId().intValue(), updatedConversionDTO);
        return updatedConversionDTO;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConversion(@PathVariable Long id) throws ConversionNotFoundException {
        conversionHistoryService.deleteConversion(id);
        conversionHistoryCache.remove(id.intValue());
    }

    @GetMapping("/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public ConversionHistoryDTO restConvert(@PathVariable Long userId,
                                           @PathVariable String fromCurrency,
                                           @PathVariable double amount,
                                           @PathVariable String toCurrency) throws UserNotFoundException, CurrencyNotFoundException {

        Currency from = currencyService.getCurrencyByCode(fromCurrency.toUpperCase());
        Currency to = currencyService.getCurrencyByCode(toCurrency.toUpperCase());

        if (from == null || to == null) {
            throw new CurrencyNotFoundException("Currency Not Found");
        }

        ConversionHistory conversion = conversionHistoryService.convertCurrency(userId, fromCurrency, amount, toCurrency);
        ConversionHistoryDTO conversionDTO = ConversionHistoryDTO.toModel(conversion);

        conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
        return conversionDTO;
    }
}