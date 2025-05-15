package com.ansarishop.app.controller;

import com.ansarishop.app.entity.Product;
import com.ansarishop.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.allProduct());
    }

    @DeleteMapping("/delete/{name}")
    public String deleteByName(@PathVariable String name){
        return productService.deleteByName(name);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestBody Product product){
        return ResponseEntity.ok(productService.update(product));
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Product> findByName(@PathVariable String name){
        return ResponseEntity.ok(productService.findByName(name));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@RequestParam long id){
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
