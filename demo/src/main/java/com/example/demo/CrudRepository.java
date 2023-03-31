package com.example.demo;

public interface CrudRepository {
    public Object[] getAll(Object o);
    public void save(Object o);
    public void delete(int id);
    public void update(Object o);
}