package com.ansarishop.app.controller;

import com.ansarishop.app.dto.request.UpdateProductStockRequest;
import com.ansarishop.app.entity.Product;
import com.ansarishop.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(Objects.requireNonNullElse(savedProduct, "Product already exists with this name"));
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

    @PostMapping("/update-stock")
    public ResponseEntity<String> updateStock(@RequestBody List<Product> products){
        productService.updateStock(products);
        log.info("checking 0 stock products");
        productService.deleteAllWithStockZero();
        return ResponseEntity.ok("Successfully updated stocks");
    }
}
