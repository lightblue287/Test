package com.fruitshop.model.enums;

public enum OrderStatus {
    ORDER_SUCCESS(1),
    ORDER_FAILED(0);

    private final int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static OrderStatus OrderStatusCode(int status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.status == status) {
                return orderStatus;
            }
        }throw new IllegalArgumentException("此訂單沒有符合的狀態碼：" + status);
    }

}
