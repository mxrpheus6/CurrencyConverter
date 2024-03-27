package com.lab1.converter.service;

import com.lab1.converter.dto.ConversionHistoryBaseDTO;
import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.entity.Currency;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import com.lab1.converter.model.ConversionResponseModel;
import com.lab1.converter.dao.ConversionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversionHistoryService {

    private final ConversionHistoryRepository conversionHistoryRepository;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Autowired
    public ConversionHistoryService(ConversionHistoryRepository conversionHistoryRepository, UserService userService, CurrencyService currencyService) {
        this.conversionHistoryRepository = conversionHistoryRepository;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    public ConversionHistoryDTO createConversion(ConversionHistory conversionHistory) {
        return ConversionHistoryDTO.toModel(conversionHistoryRepository.save(conversionHistory));
    }

    public List<ConversionHistoryDTO> getAllConversions() {
        List<ConversionHistoryDTO> conversionHistoryDTOList = new ArrayList<>();
        conversionHistoryRepository.findAll().forEach(conversion -> conversionHistoryDTOList.add(ConversionHistoryDTO.toModel(conversion)));
        return conversionHistoryDTOList;
    }

    public List<ConversionHistoryBaseDTO> getConversionsByUserId(Long userId) {
        List<ConversionHistoryBaseDTO> conversionHistoryBaseDTO = new ArrayList<>();
        for (ConversionHistory conversionHistory : conversionHistoryRepository.findConversionsByUserId(userId)) {
            conversionHistoryBaseDTO.add(ConversionHistoryBaseDTO.toModel(conversionHistory));
        }
        return conversionHistoryBaseDTO;
    }

    public ConversionHistoryDTO getConversionById(Long id) {
        ConversionHistory conversionHistory = conversionHistoryRepository.findById(id).orElseThrow(() -> new ConversionNotFoundException(id));
        return ConversionHistoryDTO.toModel(conversionHistory);
    }

    public ConversionHistoryDTO updateConversion(Long id, ConversionHistory updatedConversionHistory) {
        if (!conversionHistoryRepository.existsById(id)) {
            throw new ConversionNotFoundException(id);
        }
        updatedConversionHistory.setId(id);
        ConversionHistory savedConversion = conversionHistoryRepository.save(updatedConversionHistory); // Сохранение обновленной записи

        return ConversionHistoryDTO.toModel(savedConversion);
    }

    public void deleteConversion(Long id)  {
        if (!conversionHistoryRepository.existsById(id)) {
            throw new ConversionNotFoundException(id);
        }

        conversionHistoryRepository.deleteById(id);
    }

    public ConversionHistory convertCurrency(Long userId, String fromCurrency, double amount, String toCurrency) {
        userService.getUserById(userId);

        Currency from = currencyService.getCurrencyByCode(fromCurrency.toUpperCase());
        Currency to = currencyService.getCurrencyByCode(toCurrency.toUpperCase());

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://v6.exchangerate-api.com/v6/d6d8a4004c4b9d619b477865";
        String fullApiUrl = apiUrl + "/pair/" + from.getCode().toUpperCase() + "/" + to.getCode().toUpperCase() + "/" + amount;

        ConversionResponseModel response = restTemplate.getForObject(fullApiUrl, ConversionResponseModel.class);
        if (response != null) {
            return saveConversionToDatabase(userId, fromCurrency, amount, toCurrency, response);
        } else {
            return null;
        }
    }

    private ConversionHistory saveConversionToDatabase(Long userId, String fromCurrency, double amount, String toCurrency, ConversionResponseModel response)  {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setFromCurrency(fromCurrency.toUpperCase());
        conversionHistory.setUser(UserDTO.toEntity(userService.getUserById(userId)));
        conversionHistory.setAmount(amount);
        conversionHistory.setToCurrency(toCurrency.toUpperCase());
        conversionHistory.setConvertedAmount(response.getConversionResult());
        conversionHistoryRepository.save(conversionHistory);
        return conversionHistory;
    }
}
