package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyDatabaseService {
    // 여기에는 어떤 Bean을 주입시켜줘야 하는지??
    @Autowired
    CrudRepository repository;
}