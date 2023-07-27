package com.example.study_jpa.repository;

import com.example.study_jpa.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}
