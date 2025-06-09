package com.product.product_service.Service;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import com.product.product_service.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(AddProductRequest addProductRequest){
        log.info("Urun ekleme istegi..");
        productRepository.addProduct(addProductRequest);
        log.info(addProductRequest.getProductName(), "Urun eklendi..");

    }

    @Cacheable(value = "product", key = "#root.methodName + #id",unless = "#result == null")
    public Product getProductById(int id){

        log.info("Ürün bilgisi istendi, ürün id: {}", id);
        Product product = productRepository.getProductById(id);
        if(product != null){
            log.info("Ürün bilgisi getirildi, id: {}, name: {}", product.getId(), product.getProductName());
            return product;
        }else{
            log.warn("Ürün bilgisi bulunamadı, id: {}", id);
            return null;
        }

    }
}
