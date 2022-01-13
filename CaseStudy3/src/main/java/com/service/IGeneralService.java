package com.service;

import java.util.List;

public interface IGeneralService<T> {
    public void add(T t);

    public List<T> findAll();

    public T findById(int id);

    public void update(int id,T t);

    public void delete(int id);
}
