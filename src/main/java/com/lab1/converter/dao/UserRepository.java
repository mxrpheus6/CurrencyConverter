package com.lab1.converter.dao;

import com.lab1.converter.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}