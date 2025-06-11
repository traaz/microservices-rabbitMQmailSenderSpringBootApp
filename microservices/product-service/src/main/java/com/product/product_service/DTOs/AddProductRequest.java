package com.product.product_service.DTOs;

import jakarta.validation.constraints.*;



public class AddProductRequest {
    @NotEmpty(message = "product name gereklidir")
    private String productName;
    @NotNull(message = "price gereklidir")
    private Double price;

    public AddProductRequest(String productName, Double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
