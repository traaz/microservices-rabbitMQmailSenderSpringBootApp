package com.product.product_service.Service;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import com.product.product_service.Repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(AddProductRequest addProductRequest){
        productRepository.addProduct(addProductRequest);
    }

    @Cacheable(value = "product", key = "#root.methodName + #id")
    public Product getProductById(int id){
        return productRepository.getProductById(id);
    }
}
