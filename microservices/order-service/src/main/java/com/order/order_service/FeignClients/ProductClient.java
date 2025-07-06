package com.order.order_service.FeignClients;

import com.order.order_service.DTOs.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "product-service", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/product/{id}")
    ProductResponse getProductById(@PathVariable("id") int id);


}