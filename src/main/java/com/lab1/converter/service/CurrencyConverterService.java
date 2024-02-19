package com.lab1.converter.service;

import com.lab1.converter.entity.CurrencyConverterEntity;
import com.lab1.converter.model.ConversionResponseModel;
import com.lab1.converter.repo.CurrencyConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class CurrencyConverterService {

    private final CurrencyConverterRepository currencyConverterRepository;

    @Autowired
    public CurrencyConverterService(CurrencyConverterRepository currencyConverterRepository) {
        this.currencyConverterRepository = currencyConverterRepository;
    }

    private final String API_URL = "https://v6.exchangerate-api.com/v6/d6d8a4004c4b9d619b477865";

    public double convertCurrency(String fromCurrency, double amount, String toCurrency) {
        RestTemplate restTemplate = new RestTemplate();
        String fullApiUrl = API_URL + "/pair/" + fromCurrency.toUpperCase() + "/" + toCurrency.toUpperCase() + "/" + amount;
        try {
            ConversionResponseModel response = restTemplate.getForObject(fullApiUrl, ConversionResponseModel.class);
            if (response != null) {
                saveToDatabase(fromCurrency, amount, toCurrency, response);
                return response.getConversionResult();
            }
            else
                return -1;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Invalid currency code");
        } catch (Exception e) {
            throw e;
        }
    }

    private void saveToDatabase(String fromCurrency, double amount, String toCurrency, ConversionResponseModel response) {
        CurrencyConverterEntity entity = new CurrencyConverterEntity();
        entity.setFromCurrency(fromCurrency.toUpperCase());
        entity.setAmount(amount);
        entity.setToCurrency(toCurrency.toUpperCase());
        entity.setConvertedAmount(response.getConversionResult());

        currencyConverterRepository.save(entity);
    }
}
