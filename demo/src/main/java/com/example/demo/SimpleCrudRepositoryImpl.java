package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class SimpleCrudRepositoryImpl implements CrudRepository {
    @Override
    public Object[] getAll(Object o) {
        return new Object[0];
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Object o) {

    }
    // ... 적당히 구현했다고 가정
}

