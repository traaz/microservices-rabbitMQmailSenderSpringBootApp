package com.product.product_service.Controller;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import com.product.product_service.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProduct(@RequestBody AddProductRequest addProductRequest){
        //guard clause
        if(addProductRequest.getProductName() == null || addProductRequest.getProductName().isBlank()){
            throw new IllegalArgumentException("Ürün ismi boş olamaz");
        }
        if(addProductRequest.getPrice() == null || addProductRequest.getPrice() <=0){
            throw new IllegalArgumentException("Fiyat boş olamaz ve sıfırdan büyük olmalıdır");

        }
        productService.addProduct(addProductRequest);
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id){
       return  productService.getProductById(id);
}
}
