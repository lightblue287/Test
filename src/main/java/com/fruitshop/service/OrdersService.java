package com.fruitshop.service;

import com.fruitshop.model.Orders;
import com.fruitshop.model.Product;
import com.fruitshop.model.enums.OrderStatus;
import com.fruitshop.repository.OrdersRepository;
import com.fruitshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrdersService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrdersRepository ordersRepo;

    public List<Orders> getAllOrders() {
        return ordersRepo.findAll();
    }

    @Transactional
    public String createOrder(String customerName, Map<String, String> allProducts) {
        int totalPrice = 0;
        StringBuilder productDetail = new StringBuilder();

        for (Map.Entry<String, String> entry : allProducts.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // 只處理 id 和數量都是數字 避免加上名字的
            if (key.matches("\\d+") && value.matches("\\d+")) {
                int productId = Integer.parseInt(key);
                int quantity = Integer.parseInt(value);

                if (quantity > 0) {
                    Product product = productRepo.findById(productId).orElse(null);
                    if (product != null) {
                        if (product.getStock() < quantity) {
                            Orders failedOrder = new Orders();
                            failedOrder.setCustomerName(customerName);
                            failedOrder.setProductDetail("下單失敗：%s 數量不足".formatted(product.getName()));
                            failedOrder.setTotalPrice(0);
                            failedOrder.setCreatedTime(LocalDateTime.now());
                            failedOrder.setStatus(OrderStatus.ORDER_FAILED.getStatus()); // 失敗
                            ordersRepo.save(failedOrder);

                            return customerName + "你好，商品「" + product.getName() + "」庫存不足，請重新選擇數量";
                        }

                        //計算總金額
                        totalPrice += product.getPrice() * quantity;

                        // 扣庫存
                        product.setStock(product.getStock() - quantity);
                        productRepo.save(product);

                        // 決定單位
                        String unit = "";
                        String name = product.getName();
                        if ("蘋果".equals(name) || "鳳梨".equals(name)) {
                            unit = "顆";
                        } else if ("香蕉".equals(name)) {
                            unit = "條";
                        } else if ("西瓜".equals(name)) {
                            unit = "片";
                        } else {
                            continue;
                        }

                        // 建立商品摘要文字
                        productDetail.append(product.getName())
                                .append(quantity)
                                .append(unit)
                                .append("、");
                    }
                }
            }
        }

        // 如果沒有成功購買任何東西
        if (totalPrice == 0) {
            return customerName + "你好，沒有成功購買任何商品，請確認庫存與購買數量。";
        }

        // 若是有商品 就移除最後一個多的頓號
        if (productDetail.length() > 0) {
            productDetail.setLength(productDetail.length() - 1);
        }

        // 建立訂單
        Orders order = new Orders();
        order.setCustomerName(customerName);
        order.setProductDetail(productDetail.toString());
        order.setTotalPrice(totalPrice);
        order.setCreatedTime(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER_SUCCESS.getStatus());

        ordersRepo.save(order);

        return "%s 你好，您購買了：%s，總計 %d 元".formatted(customerName, productDetail, totalPrice);
    }
}
