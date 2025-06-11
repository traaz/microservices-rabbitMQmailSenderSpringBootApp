package com.order.order_service.FlientClients;

import com.order.order_service.DTOs.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping("/customer/{id}")
    CustomerResponse getCustomerById(@PathVariable("id") int id);
}
