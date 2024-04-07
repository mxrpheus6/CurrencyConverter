package com.lab1.converter.service;

import com.lab1.converter.dao.CurrencyRepository;
import com.lab1.converter.dto.CurrencyDTO;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.Currency;
import com.lab1.converter.exceptions.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lab1.converter.model.ExchangeRateApiResponseModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency getCurrencyByCode(String code) {
        if (!currencyRepository.existsByCodeIgnoreCase(code)) {
            throw new CurrencyNotFoundException(code);
        }
        return currencyRepository.findByCodeIgnoreCase(code);
    }

    public List<CurrencyDTO> getAllCurrencies() {
        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        currencyRepository.findAll().forEach(currency -> currencyDTOList.add(CurrencyDTO.toModel(currency)));
        return currencyDTOList;
    }

    public void fetchAndSaveCurrencyRates() {
        currencyRepository.deleteAll();

        String apiUrl = "https://v6.exchangerate-api.com/v6/d6d8a4004c4b9d619b477865/latest/USD";  // Replace with the actual API endpoint
        ExchangeRateApiResponseModel response = new RestTemplate().getForObject(apiUrl, ExchangeRateApiResponseModel.class);

        if (response != null && "success".equals(response.getResult())) {
            response.getConversionRates().forEach((code, rate) -> {
                Currency currency = new Currency();
                currency.setCode(code);
                currency.setRate(rate);
                currencyRepository.save(currency);
            });
        }
    }
}
