package com.fruitshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "product_detail")
    private String productDetail;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "status")
    private Integer status;

}

