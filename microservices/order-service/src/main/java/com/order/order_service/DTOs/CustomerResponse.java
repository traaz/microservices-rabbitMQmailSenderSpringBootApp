package com.order.order_service.DTOs;

public class CustomerResponse {
    private int id;
    private String email;

    public CustomerResponse(){

    }

    public CustomerResponse(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
