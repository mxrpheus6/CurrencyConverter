package com.lab1.converter.dao;

import com.lab1.converter.entity.ConversionHistory;
import org.springframework.data.repository.CrudRepository;

public interface ConversionHistoryRepository extends CrudRepository <ConversionHistory, Long> {
}
