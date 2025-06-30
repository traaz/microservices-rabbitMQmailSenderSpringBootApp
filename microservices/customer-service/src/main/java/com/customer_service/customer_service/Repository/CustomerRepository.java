package com.customer_service.customer_service.Repository;

import com.customer_service.customer_service.DTOs.AddCustomerRequest;
import com.customer_service.customer_service.Model.Customer;
import com.customer_service.customer_service.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addCustomer(AddCustomerRequest addCustomerRequest){
        try{
            String insertCustomerQuery = "INSERT INTO CUSTOMERS (email) VALUES (:emailValue)";

            Map<String, Object> param = new HashMap<>();
            param.put("emailValue", addCustomerRequest.getEmail());

            MapSqlParameterSource insertParamSource = new MapSqlParameterSource(param);

            namedParameterJdbcTemplate.update(insertCustomerQuery, insertParamSource);
        } catch (Exception e) {
            log.error("Customer eklemede hata :{}, mail: {}", e.getMessage(), addCustomerRequest.getEmail());
        }


    }

    public Optional<Customer> getCustomerById(int id){
        try{
            String getCustomerByIdQuery = "SELECT * FROM CUSTOMERS WHERE id = :idValue";
            Map<String, Object> params = new HashMap<>();
            params.put("idValue", id);
            MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
            Customer customer =  namedParameterJdbcTemplate.queryForObject(getCustomerByIdQuery, paramSource,  new BeanPropertyRowMapper<>(Customer.class));
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
