package com.fruitshop.service;

import com.fruitshop.model.Orders;
import com.fruitshop.model.Product;
import com.fruitshop.repository.OrdersRepository;
import com.fruitshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class OrdersServiceMockTest {

    @Autowired
    private OrdersService ordersSvc;

    @MockBean
    private OrdersRepository ordersRepo;

    @Test
    public void createOrderTest() {
        //模擬訂單資料
        Map<String, String> allProducts = new HashMap<>();
        allProducts.put("1", "2"); //蘋果2顆

        //模擬訂單儲存後的內容
        Orders mockOrder = new Orders();
        mockOrder.setId(1);
        mockOrder.setCustomerName("測試用戶");
        mockOrder.setProductDetail("蘋果2顆");
        mockOrder.setTotalPrice(60);
        mockOrder.setStatus(1);

        //模擬 repository.save()儲存訂單
        Mockito.when(ordersRepo.save(any(Orders.class))).thenReturn(mockOrder);

        //訂單建立
        String result = ordersSvc.createOrder("測試用戶", allProducts);

        //驗證訊息是否顯示正確
        String successMsg = "測試用戶 你好，您購買了：蘋果2顆，總計 20 元";
        assertEquals(successMsg, result);

        //確認有儲存資料進DB且只有一次
        Mockito.verify(ordersRepo, Mockito.times(1)).save(any(Orders.class));
    }
}

