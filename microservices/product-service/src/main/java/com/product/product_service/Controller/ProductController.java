package com.product.product_service.Controller;

import com.product.product_service.DTOs.AddProductRequest;
import com.product.product_service.Model.Product;
import com.product.product_service.Service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProduct(@RequestBody AddProductRequest addProductRequest){
        productService.addProduct(addProductRequest);
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id){
       return  productService.getProductById(id);
}
}
