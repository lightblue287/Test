package com.fruitshop.service;

import com.fruitshop.model.Product;
import com.fruitshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findByStatus(1);
    }

    public Product findById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("找不到商品編號為： " + id + "的水果品項"));
    }

    public Product createProduct(Product product) {
        product.setStatus(1); // 預設為上架
        return productRepo.save(product);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        Product productExist = productRepo.findById(id)
                              .orElseThrow(() -> new IllegalArgumentException("找不到商品編號為：" + id + "的水果品項，無法更新"));

        productExist.setName(updatedProduct.getName());
        productExist.setPrice(updatedProduct.getPrice());
        productExist.setStock(updatedProduct.getStock());
        return productRepo.save(productExist);
    }

    public void deleteProduct(Integer id) {
        Product productExist = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("找不到編號為：" + id + "的水果品項，無法刪除"));
        productExist.setStatus(0); // 商品狀態設為0=下架
        productRepo.save(productExist);
    }
}
