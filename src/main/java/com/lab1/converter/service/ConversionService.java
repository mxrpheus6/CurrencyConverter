package com.lab1.converter.service;

import com.lab1.converter.dto.ConversionDTO;
import com.lab1.converter.dto.UserDTO;
import com.lab1.converter.entity.Conversion;
import com.lab1.converter.exceptions.ConversionNotFoundException;
import com.lab1.converter.exceptions.UserNotFoundException;
import com.lab1.converter.model.ConversionResponseModel;
import com.lab1.converter.dao.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final UserService userService;

    @Autowired
    public ConversionService(ConversionRepository conversionRepository, UserService userService) {
        this.conversionRepository = conversionRepository;
        this.userService = userService;
    }

    public ConversionDTO createConversion(Conversion conversion) {
        return ConversionDTO.toModel(conversionRepository.save(conversion));
    }

    public Iterable<ConversionDTO> getAllConversions() {
        List<ConversionDTO> conversionDTOList = new ArrayList<>();
        conversionRepository.findAll().forEach(conversion -> conversionDTOList.add(ConversionDTO.toModel(conversion)));
        return conversionDTOList;
    }

    public ConversionDTO getConversionById(Long id) throws ConversionNotFoundException {
        Conversion conversion = conversionRepository.findById(id).orElseThrow(() -> new ConversionNotFoundException("Conversion not found"));
        return ConversionDTO.toModel(conversion);
    }

    public ConversionDTO updateConversion(Long id, Conversion updatedConversion) throws ConversionNotFoundException {
        if (!conversionRepository.existsById(id))
            throw new ConversionNotFoundException("Conversion not found");

        updatedConversion.setId(id);
        return ConversionDTO.toModel(conversionRepository.save(updatedConversion));
    }

    public void deleteConversion(Long id) throws ConversionNotFoundException {
        if (!conversionRepository.existsById(id))
            throw new ConversionNotFoundException("Conversion not found");
        conversionRepository.deleteById(id);
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
        Conversion conversion = new Conversion();
        conversion.setFromCurrency(fromCurrency.toUpperCase());
        conversion.setUser(UserDTO.toEntity(userService.getUserById(userId)));
        conversion.setAmount(amount);
        conversion.setToCurrency(toCurrency.toUpperCase());
        conversion.setConvertedAmount(response.getConversionResult());
        conversionRepository.save(conversion);
    }
}
