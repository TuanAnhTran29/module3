package com.service;

import com.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private static List<Product> productList= new ArrayList<>();

    static {
        productList.add(new Product(1,"Rice",50000,"RICE","Tuan Anh"));
        productList.add(new Product(2,"Chicken",1231000,"CHICKEN","Mark"));
        productList.add(new Product(3,"Dog",32000,"RICE","John"));
        productList.add(new Product(4,"Cookies",100000,"RICE","Kinh Do"));
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        productList.add(product);
    }

    @Override
    public Product findById(int id) {
        Product product= null;
        for (Product p: productList) {
            if (p.getId() == id){
                product= p;
            }
        }
        return product;
    }

    @Override
    public Product findByName(String name) {
        Product product= null;
        for (Product p: productList) {
            if (p.getName().equals(name)){
                product=p;
            }
        }
        return product;
    }

    @Override
    public void update(int id, Product product) {
        int index= 0;
        for (Product p: productList) {
            if (p.getId() == id){
                productList.set(index,product);
                break;
            }
            index++;
        }
    }

    @Override
    public void remove(int id) {
        int index= 0;
        for (Product p: productList) {
            if (p.getId() == id){
                productList.remove(productList.get(index));
                break;
            }
            index++;
        }
    }
}
