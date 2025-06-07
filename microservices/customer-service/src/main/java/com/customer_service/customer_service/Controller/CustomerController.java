package com.customer_service.customer_service.Controller;

import com.customer_service.customer_service.DTOs.AddCustomerRequest;
import com.customer_service.customer_service.Model.Customer;
import com.customer_service.customer_service.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void addCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        customerService.addCustomer(addCustomerRequest);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id){
        return  customerService.getCustomerById(id);
    }
}