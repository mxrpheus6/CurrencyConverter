package com.lab1.converter.controller;

import com.lab1.converter.cache.ConversionHistoryCache;
import com.lab1.converter.dto.ConversionHistoryBaseDTO;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.service.RequestCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lab1.converter.service.ConversionHistoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/conversions")
@CrossOrigin
public class ConversionHistoryController {
    
    private final ConversionHistoryService conversionHistoryService;
    private final ConversionHistoryCache conversionHistoryCache;
    private final RequestCounterService requestCounterService;

    @Autowired
    public ConversionHistoryController(ConversionHistoryService currencyConverterService,
                                       ConversionHistoryCache conversionHistoryCache,
                                       RequestCounterService requestCounterService) {
        this.conversionHistoryService = currencyConverterService;
        this.conversionHistoryCache = conversionHistoryCache;
        this.requestCounterService = requestCounterService;
    }

    @PostMapping("/create")
    public ResponseEntity<ConversionHistoryDTO> createConversion(@RequestBody ConversionHistory conversionHistory) {
        log.info("POST endpoint /conversions/create - " + requestCounterService.incrementAndGet());
        ConversionHistoryDTO createdConversion = conversionHistoryService.createConversion(conversionHistory);
        conversionHistoryCache.put(conversionHistory.getId().intValue(), createdConversion);
        return new ResponseEntity<>(createdConversion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConversionHistoryDTO>> getAllConversions() {
        log.info("GET endpoint /conversions" + requestCounterService.incrementAndGet());
        List<ConversionHistoryDTO> conversionHistoryDTOList = conversionHistoryService.getAllConversions();
        for (ConversionHistoryDTO conversionDTO: conversionHistoryDTOList) {
            conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
        }
        return new ResponseEntity<>(conversionHistoryDTOList, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ConversionHistoryBaseDTO>> getConversionsByUserId(@PathVariable Long userId) {
        log.info("GET endpoint /conversions/user/{userId}" + requestCounterService.incrementAndGet());
        return new ResponseEntity<>(conversionHistoryService.getConversionsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversionHistoryDTO> getConversionById(@PathVariable Long id) {
        log.info("GET endpoint /conversions/{id}" + requestCounterService.incrementAndGet());
        if (conversionHistoryCache.contains(id.intValue())) {
            return new ResponseEntity<>(conversionHistoryCache.get(id.intValue()), HttpStatus.OK);
        } else {
            ConversionHistoryDTO conversionDTO = conversionHistoryService.getConversionById(id);
            conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
            return new ResponseEntity<>(conversionDTO, HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ConversionHistoryDTO> updateConversion(@PathVariable Long id,
                                                                 @RequestBody ConversionHistory conversionHistory) {
        log.info("PUT endpoint /conversions/update/{id}" + requestCounterService.incrementAndGet());
        ConversionHistoryDTO updatedConversionDTO = conversionHistoryService.updateConversion(id, conversionHistory);
        conversionHistoryCache.put(updatedConversionDTO.getId().intValue(), updatedConversionDTO);
        return new ResponseEntity<>(updatedConversionDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConversion(@PathVariable Long id)  {
        log.info("DELETE endpoint /conversions/delete/{id}" + requestCounterService.incrementAndGet());
        conversionHistoryService.deleteConversion(id);
        conversionHistoryCache.remove(id.intValue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}")
    public ResponseEntity<ConversionHistoryDTO> restConvert(@PathVariable Long userId,
                                           @PathVariable String fromCurrency,
                                           @PathVariable double amount,
                                           @PathVariable String toCurrency) {
        log.info("GET endpoint /conversions/convert/user/{userId}/from/{fromCurrency}/amount/{amount}/to/{toCurrency}"
                + requestCounterService.incrementAndGet());

        ConversionHistory conversion = conversionHistoryService.convertCurrency(userId, fromCurrency, amount, toCurrency);
        ConversionHistoryDTO conversionDTO = ConversionHistoryDTO.toModel(conversion);

        conversionHistoryCache.put(conversionDTO.getId().intValue(), conversionDTO);
        return new ResponseEntity<>(conversionDTO, HttpStatus.OK);
    }
}