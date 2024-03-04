package com.lab1.converter.service;

import com.lab1.converter.dto.ConversionHistoryDTO;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.ConversionHistory;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import com.lab1.converter.exceptions.UserNotFoundException;
import com.lab1.converter.model.ConversionResponseModel;
import com.lab1.converter.dao.ConversionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversionHistoryService {
    private static final String ERROR_CONVERSION_NOT_FOUND = "Conversion not found";

    private final ConversionHistoryRepository conversionHistoryRepository;
    private final UserService userService;

    @Autowired
    public ConversionHistoryService(ConversionHistoryRepository conversionHistoryRepository, UserService userService) {
        this.conversionHistoryRepository = conversionHistoryRepository;
        this.userService = userService;
    }

    public ConversionHistoryDTO createConversion(ConversionHistory conversionHistory) {
        return ConversionHistoryDTO.toModel(conversionHistoryRepository.save(conversionHistory));
    }

    public Iterable<ConversionHistoryDTO> getAllConversions() {
        List<ConversionHistoryDTO> conversionHistoryDTOList = new ArrayList<>();
        conversionHistoryRepository.findAll().forEach(conversion -> conversionHistoryDTOList.add(ConversionHistoryDTO.toModel(conversion)));
        return conversionHistoryDTOList;
    }

    public ConversionHistoryDTO getConversionById(Long id) throws ConversionNotFoundException {
        ConversionHistory conversionHistory = conversionHistoryRepository.findById(id).orElseThrow(() -> new ConversionNotFoundException(ERROR_CONVERSION_NOT_FOUND));
        return ConversionHistoryDTO.toModel(conversionHistory);
    }

    public ConversionHistoryDTO updateConversion(Long id, ConversionHistory updatedConversionHistory) throws ConversionNotFoundException {
        if (!conversionHistoryRepository.existsById(id))
            throw new ConversionNotFoundException(ERROR_CONVERSION_NOT_FOUND);

        updatedConversionHistory.setId(id);
        return ConversionHistoryDTO.toModel(conversionHistoryRepository.save(updatedConversionHistory));
    }

    public void deleteConversion(Long id) throws ConversionNotFoundException {
        if (!conversionHistoryRepository.existsById(id))
            throw new ConversionNotFoundException(ERROR_CONVERSION_NOT_FOUND);
        conversionHistoryRepository.deleteById(id);
    }

    public double convertCurrency(Long userId, String fromCurrency, double amount, String toCurrency) throws UserNotFoundException {
        try {
            userService.getUserById(userId);
        }
        catch (UserNotFoundException exp) {
            throw new UserNotFoundException("User not found");
        }

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://v6.exchangerate-api.com/v6/d6d8a4004c4b9d619b477865";
        String fullApiUrl = apiUrl + "/pair/" + fromCurrency.toUpperCase() + "/" + toCurrency.toUpperCase() + "/" + amount;
        try {
            ConversionResponseModel response = restTemplate.getForObject(fullApiUrl, ConversionResponseModel.class);
            if (response != null) {
                saveConversionToDatabase(userId, fromCurrency, amount, toCurrency, response);
                return response.getConversionResult();
            }
            else
                return -1;
        } catch (HttpClientErrorException.NotFound e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    private void saveConversionToDatabase(Long userId, String fromCurrency, double amount, String toCurrency, ConversionResponseModel response) throws UserNotFoundException {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setFromCurrency(fromCurrency.toUpperCase());
        conversionHistory.setUser(UserDTO.toEntity(userService.getUserById(userId)));
        conversionHistory.setAmount(amount);
        conversionHistory.setToCurrency(toCurrency.toUpperCase());
        conversionHistory.setConvertedAmount(response.getConversionResult());
        conversionHistoryRepository.save(conversionHistory);
    }
}
