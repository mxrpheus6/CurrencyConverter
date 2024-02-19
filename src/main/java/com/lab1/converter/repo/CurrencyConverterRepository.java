package com.lab1.converter.repo;

import com.lab1.converter.entity.CurrencyConverterEntity;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyConverterRepository extends CrudRepository <CurrencyConverterEntity, Long> {
}
