package com.fruitshop.controller;


import com.fruitshop.service.OrdersService;
import com.fruitshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersSvc;

    @Autowired
    private ProductService productSvc;

    // 顯示下單頁面 + 歷史訂單
    @GetMapping
    public String showOrderPage(Model model,
                                @RequestParam(value = "successMsg", required = false) String successMsg) {
        model.addAttribute("products", productSvc.getAllProducts());
        model.addAttribute("orders", ordersSvc.getAllOrders());
        if (successMsg != null) {
            model.addAttribute("successMsg", successMsg);
        }
        return "orders";
    }
    // 處理表單送出，下單建立訂單
    @PostMapping("/add")
    public String addOrder(@RequestParam String customerName,
                           @RequestParam Map<String, String> allProducts,
                           RedirectAttributes redirectAttrs) {
        String msg = ordersSvc.createOrder(customerName, allProducts);
        redirectAttrs.addFlashAttribute("successMsg", msg);

        return "redirect:/orders";
    }
}
