package com.order.order_service.DTOs;

public class AddOrderRequest {

    private int productId;
    private int customerId;

    public AddOrderRequest(int customerId, int productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
