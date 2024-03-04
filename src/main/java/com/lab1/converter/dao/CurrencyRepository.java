package com.lab1.converter.dao;

import com.lab1.converter.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency findByCodeIgnoreCase(String code);
}

