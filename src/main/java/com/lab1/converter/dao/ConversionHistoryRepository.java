package com.lab1.converter.dao;

import com.lab1.converter.entity.ConversionHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionHistoryRepository extends CrudRepository <ConversionHistory, Long> {

    @Query("SELECT ch FROM ConversionHistory ch WHERE ch.user.id = :userId")
    List<ConversionHistory> findConversionsByUserId(@Param("userId") Long userId);

}
