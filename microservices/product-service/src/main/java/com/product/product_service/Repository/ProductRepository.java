package com.product.product_service.Repository;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addProduct(AddProductRequest addProductRequest) {
        String insertProductQuery = "INSERT INTO PRODUCTS (product_name, price) VALUES (:nameValue, :priceValue)";

        Map<String, Object> params = new HashMap<>();
        params.put("nameValue", addProductRequest.getProductName());
        params.put("priceValue", addProductRequest.getPrice());

        MapSqlParameterSource insertParamSource = new MapSqlParameterSource(params);

        namedParameterJdbcTemplate.update(insertProductQuery, insertParamSource);

    }

    public Product getProductById(int id) {
        String getProductByIdQuery = "SELECT * FROM PRODUCTS WHERE id = :idValue";
        Map<String, Object> params = new HashMap<>();
        params.put("idValue", id);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
        return namedParameterJdbcTemplate.queryForObject(getProductByIdQuery, paramSource, new BeanPropertyRowMapper<>(Product.class));
    }
}