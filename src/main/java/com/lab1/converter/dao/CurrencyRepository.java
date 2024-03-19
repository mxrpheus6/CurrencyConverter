package com.lab1.converter.dao;

import com.lab1.converter.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}