package com.customer_service.customer_service.Repository;

import com.customer_service.customer_service.DTOs.AddCustomerRequest;
import com.customer_service.customer_service.Model.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addCustomer(AddCustomerRequest addCustomerRequest){
        String insertCustomerQuery = "INSERT INTO CUSTOMERS (email) VALUES (:emailValue)";

        Map<String, Object> param = new HashMap<>();
        param.put("emailValue", addCustomerRequest.getEmail());

        MapSqlParameterSource insertParamSource = new MapSqlParameterSource(param);

        namedParameterJdbcTemplate.update(insertCustomerQuery, insertParamSource);

    }

    public Customer getCustomerById(int id){
        String getCustomerByIdQuery = "SELECT * FROM CUSTOMERS WHERE id = :idValue";
        Map<String, Object> params = new HashMap<>();
        params.put("idValue", id);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
        return namedParameterJdbcTemplate.queryForObject(getCustomerByIdQuery, paramSource,  new BeanPropertyRowMapper<>(Customer.class));
    }
}
