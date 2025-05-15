package com.ansarishop.app.service.impl;

import com.ansarishop.app.entity.Product;
import com.ansarishop.app.exceptions.ProductNotFoundException;
import com.ansarishop.app.repository.ProductRepository;
import com.ansarishop.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> allProduct() {
        return productRepository.findAll();
    }

    @Override
    public String deleteByName(String name) {
        String response = "";
        Product product = productRepository.findByName(name);
        if(product == null)
            response = response + "No product found with this name";
        else{
            productRepository.delete(product);
            response += "Successfully deleted !!";
        }
        return response;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findByName(String name) throws ProductNotFoundException {
        Product product = productRepository.findByName(name);
        if(product == null)
            throw new ProductNotFoundException("Sorry, No Product found with this name");
        else return product;
    }

    @Override
    public String deleteById(long id) {
        productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Sorry, Product not found with this ID"));
        productRepository.deleteById(id);
        return "Deleted Successfully !!";
    }
}
