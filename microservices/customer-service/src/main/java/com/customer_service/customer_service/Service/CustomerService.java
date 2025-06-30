package com.customer_service.customer_service.Service;

import com.customer_service.customer_service.DTOs.AddCustomerRequest;
import com.customer_service.customer_service.Model.Customer;
import com.customer_service.customer_service.Repository.CustomerRepository;
import org.slf4j.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(AddCustomerRequest addCustomerRequest){
        customerRepository.addCustomer(addCustomerRequest);
    }

    @Cacheable(value = "customer", key = "#root.methodName + #id",unless = "#result == null")
    public Customer getCustomerById(int id){

        log.info("Customer bilgisi istendi, ürün id: {}", id);
        Optional<Customer> customer = customerRepository.getCustomerById(id);
        if(customer.isPresent()){
            log.info("Customer bilgisi getirildi, id: {}, name: {}", customer.get().getId(), customer.get().getEmail());
            return customer.get();
        }else{
            log.warn("Customer bilgisi bulunamadı, id: {}", id);
            throw new IllegalArgumentException("Customer bulunamadı, id: " + id);
        }

    }
    }
