package com.ansarishop.app.service.impl;

import com.ansarishop.app.controller.AuthController;
import com.ansarishop.app.entity.Product;
import com.ansarishop.app.exceptions.ProductNotFoundException;
import com.ansarishop.app.repository.ProductRepository;
import com.ansarishop.app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product saveProduct(Product product) {
        product.setName(product.getName().trim());
        logger.info("product name in saved product method");
        Product savedproduct = productRepository.findByName(product.getName());
        if(savedproduct == null)
            return productRepository.save(product);
        else
            //throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exist with this name");
            return null;

    }

    @Override
    public List<Product> allProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream().
                sorted(Comparator.comparing(Product::getId).reversed()).toList();
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
        product.setName(product.getName().trim());
        logger.info("product name in update product method"+product.getName());
        return productRepository.save(product);
    }

    @Override
    public Product findByName(String name) throws ProductNotFoundException {
        logger.info("Product name :"+name);
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
