package com.lab1.converter.controller;

import com.lab1.converter.cache.ConversionHistoryCache;
import com.lab1.converter.dto.ConversionHistoryBaseDTO;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.service.CurrencyService;
import com.lab1.converter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lab1.converter.service.ConversionHistoryService;

import java.util.List;

@Slf4j
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
    public ResponseEntity<ConversionHistoryDTO> createConversion(@RequestBody ConversionHistory conversionHistory) {
        log.info("POST endpoint /conversions/create was called");
        ConversionHistoryDTO createdConversion = conversionHistoryService.createConversion(conversionHistory);
        conversionHistoryCache.put(conversionHistory.getId().intValue(), createdConversion);
        return ResponseEntity.ok(conversionHistoryService.createConversion(conversionHistory));
    }

    @GetMapping
    public ResponseEntity<List<ConversionHistoryDTO>> getAllConversions() {
        log.info("GET endpoint /conversions was called");
        List<ConversionHistoryDTO> conversionHistoryDTOList = conversionHistoryService.getAllConversions();
        for (ConversionHistoryDTO conversionDTO: conversionHistoryDTOList) {
            conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
        }
        return ResponseEntity.ok(conversionHistoryDTOList);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ConversionHistoryBaseDTO>> getConversionsByUserId(@PathVariable Long userId) {
        log.info("GET endpoint /conversions/user/{userId} was called");
        return ResponseEntity.ok(conversionHistoryService.getConversionsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversionHistoryDTO> getConversionById(@PathVariable Long id) {
        log.info("GET endpoint /conversions/{id} was called");
        if (conversionHistoryCache.contains(id.intValue())) {
            return ResponseEntity.ok(conversionHistoryCache.get(id.intValue()));
        } else {
            ConversionHistoryDTO conversionDTO = conversionHistoryService.getConversionById(id);
            conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
            return ResponseEntity.ok(conversionDTO);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ConversionHistoryDTO> updateConversion(@PathVariable Long id,
                                                                 @RequestBody ConversionHistory conversionHistory) {
        log.info("PUT endpoint /conversions/update/{id} was called");
        ConversionHistoryDTO updatedConversionDTO = conversionHistoryService.updateConversion(id, conversionHistory);
        conversionHistoryCache.put(updatedConversionDTO.getId().intValue(), updatedConversionDTO);
        return ResponseEntity.ok(updatedConversionDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConversion(@PathVariable Long id)  {
        log.info("DELETE endpoint /conversions/delete/{id} was called");
        conversionHistoryService.deleteConversion(id);
        conversionHistoryCache.remove(id.intValue());
        return ResponseEntity.ok("Conversion with id=" + id + " was successfully deleted");
    }

    @GetMapping("/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public ResponseEntity<ConversionHistoryDTO> restConvert(@PathVariable Long userId,
                                           @PathVariable String fromCurrency,
                                           @PathVariable double amount,
                                           @PathVariable String toCurrency) {
        log.info("GET endpoint /conversions//convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency} was called");
        ConversionHistory conversion = conversionHistoryService.convertCurrency(userId, fromCurrency, amount, toCurrency);
        ConversionHistoryDTO conversionDTO = ConversionHistoryDTO.toModel(conversion);

        conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
        return ResponseEntity.ok(conversionDTO);
    }
}