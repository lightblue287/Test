package com.fruitshop.model.enums;

public enum ProductStatus {
    ON_SHELF(1),
    OFF_SHELF(0);

    private final int status;

    ProductStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ProductStatus productStatusCode(int status) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.status == status) {
                return productStatus;
            }
        }throw new IllegalArgumentException("此商品沒有符合的狀態碼：" + status);
    }

}
