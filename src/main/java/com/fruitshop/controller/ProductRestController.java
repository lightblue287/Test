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
    public ResponseEntity<?> findAll() {
        List<Product> products = productSvc.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Product product = productSvc.findById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            productSvc.createProduct(product);
            return ResponseEntity.ok("商品：" + product.getName() + " 已成功新增");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        try {
            productSvc.updateProduct(id, updatedProduct);
            return ResponseEntity.ok("商品:"+ updatedProduct.getName()+"已成功修改");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            Product deletedProduct = productSvc.deleteProduct(id);
            return ResponseEntity.ok("商品：" + deletedProduct.getName() + "已成功下架");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
