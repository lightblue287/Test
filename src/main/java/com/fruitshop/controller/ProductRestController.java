package com.fruitshop.controller;

import com.fruitshop.model.Product;
import com.fruitshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restapi")
public class ProductRestController {

    @Autowired
    private ProductService productSvc;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productSvc.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        try {
            Product product = productSvc.findById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            Product created = productSvc.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        try {
            Product updated = productSvc.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            productSvc.deleteProduct(id);
            return ResponseEntity.ok("商品已成功下架");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
