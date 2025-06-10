package com.fruitshop.service;

import com.fruitshop.model.Product;
import com.fruitshop.model.enums.ProductStatus;
import com.fruitshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findByStatus(ProductStatus.ON_SHELF.getStatus());
    }

    public Product findById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("找不到商品編號為： " + id + "的水果品項"));
    }

    public void createProduct(Product product) {
        if(productRepo.existsByName(product.getName())) {
            throw new IllegalArgumentException("此商品名稱："+ product.getName() + "已存在，無法新增");
        }
        product.setStatus(ProductStatus.ON_SHELF.getStatus()); // 預設為上架
        productRepo.save(product);
    }

    public void updateProduct(Integer id, Product updatedProduct) {
        Product productExist = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("找不到商品編號為：" + id + "的水果品項，無法更新"));

        productExist.setName(updatedProduct.getName());
        productExist.setPrice(updatedProduct.getPrice());
        productExist.setStock(updatedProduct.getStock());
        productRepo.save(productExist);
    }

    public Product deleteProduct(Integer id) {
        Product productExist = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("找不到編號為：" + id + "的水果品項，無法刪除"));
            if(! productExist.getStatus().equals(ProductStatus.ON_SHELF.getStatus())) {
                throw new IllegalArgumentException("商品名稱：" + productExist.getName() + " 不在架上，無法刪除");
            }else {
            productExist.setStatus(ProductStatus.OFF_SHELF.getStatus());
        }

        return productRepo.save(productExist);
    }
}
