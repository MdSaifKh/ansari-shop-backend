package com.ansarishop.app.service.impl;

import com.ansarishop.app.controller.AuthController;
import com.ansarishop.app.dto.request.UpdateProductStockRequest;
import com.ansarishop.app.entity.Product;
import com.ansarishop.app.exceptions.ProductNotFoundException;
import com.ansarishop.app.repository.ProductRepository;
import com.ansarishop.app.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;


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
    @Transactional
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
    @Transactional
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
    @Transactional
    public String deleteById(long id) {
        productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Sorry, Product not found with this ID"));
        productRepository.deleteById(id);
        return "Deleted Successfully !!";
    }

    @Override
    @Transactional
    public void updateStock(List<Product> products) {
        this.productRepository.saveAll(products);
    }

    @Transactional
    @Override
    public void deleteAllWithStockZero() {
        logger.info("checking if product present with stock 0");
        List<Product> productWithStockZero = this.productRepository.findByStock(0);

        if(!productWithStockZero.isEmpty()){
            logger.info("Deleting stock with stock 0");
            this.productRepository.deleteAll(productWithStockZero);
        }else{
            logger.info("No records with 0 stock found");
        }
    }
}
