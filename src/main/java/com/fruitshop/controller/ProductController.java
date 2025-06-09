package com.fruitshop.controller;

import com.fruitshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productSvc;

    @GetMapping
    public String ProductList(Model model) {
        model.addAttribute("products", productSvc.getAllProducts());
        return "index";
    }
}
