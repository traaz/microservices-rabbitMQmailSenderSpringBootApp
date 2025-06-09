package com.product.product_service.Repository;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addProduct(AddProductRequest addProductRequest) {
        try{
            String insertProductQuery = "INSERT INTO PRODUCTS (product_name, price) VALUES (:nameValue, :priceValue)";

            Map<String, Object> params = new HashMap<>();
            params.put("nameValue", addProductRequest.getProductName());
            params.put("priceValue", addProductRequest.getPrice());

            MapSqlParameterSource insertParamSource = new MapSqlParameterSource(params);

            namedParameterJdbcTemplate.update(insertProductQuery, insertParamSource);
        } catch (Exception e) {
            log.error("Ürün eklenmedi, DB hatası: {}, urun: {}", e.getMessage(),addProductRequest.getProductName() ,e);

        }


    }

    public Product getProductById(int id) {
        try{
            String getProductByIdQuery = "SELECT * FROM PRODUCTS WHERE id = :idValue";
            Map<String, Object> params = new HashMap<>();
            params.put("idValue", id);
            MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
            return namedParameterJdbcTemplate.queryForObject(getProductByIdQuery, paramSource, new BeanPropertyRowMapper<>(Product.class));
        } catch (Exception e) {
           return null;
        }

    }
}