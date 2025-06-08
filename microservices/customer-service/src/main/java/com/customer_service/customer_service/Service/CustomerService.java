package com.customer_service.customer_service.Service;

import com.customer_service.customer_service.DTOs.AddCustomerRequest;
import com.customer_service.customer_service.Model.Customer;
import com.customer_service.customer_service.Repository.CustomerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(AddCustomerRequest addCustomerRequest){
        customerRepository.addCustomer(addCustomerRequest);
    }

    @Cacheable(value = "customer", key = "#root.methodName + #id")
    public Customer getCustomerById(int id){
        return customerRepository.getCustomerById(id);
    }
}