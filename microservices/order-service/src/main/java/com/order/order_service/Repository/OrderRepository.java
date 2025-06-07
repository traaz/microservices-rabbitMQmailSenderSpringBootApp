package com.order.order_service.Repository;

import com.order.order_service.DTOs.AddOrderRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public OrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addOrder(AddOrderRequest addOrderRequest){
        //orderdate db tarafından eklencek
        String addOrderQuery = "INSERT INTO ORDERS (product_id,customer_id) VALUES (:productIdValue, :customerIdValue)";

        Map<String, Object> params = new HashMap<>();
        params.put("productIdValue", addOrderRequest.getProductId());
        params.put("customerIdValue", addOrderRequest.getCustomerId());

        MapSqlParameterSource insertParamSource = new MapSqlParameterSource(params);

        namedParameterJdbcTemplate.update(addOrderQuery, insertParamSource);

    }}