package com.lab1.converter.dao;

import com.lab1.converter.entity.Conversion;
import org.springframework.data.repository.CrudRepository;

public interface ConversionRepository extends CrudRepository <Conversion, Long> {
}
