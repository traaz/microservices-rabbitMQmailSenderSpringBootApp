package com.order.order_service.Controller;

import com.order.order_service.DTOs.AddOrderRequest;
import com.order.order_service.Service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping()
    public void addOrder(@RequestBody AddOrderRequest addOrderRequest){
        orderService.addOrder(addOrderRequest);

    }
}