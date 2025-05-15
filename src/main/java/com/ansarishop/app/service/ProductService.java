package com.ansarishop.app.service;

import com.ansarishop.app.entity.Product;
import com.ansarishop.app.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> allProduct();
    String deleteByName(String name);
    Product update(Product product);
    Product findByName(String name) throws ProductNotFoundException;
    String deleteById(long id);
}
