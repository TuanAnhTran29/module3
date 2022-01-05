package com.service;

import com.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> findAll();
    void save(Product product);
    Product findById(int id);
    Product findByName(String name);
    void update(int id,Product product);
    void remove(int id);
}
