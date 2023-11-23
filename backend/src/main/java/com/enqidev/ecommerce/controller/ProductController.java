package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
//        return savedProduct;
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED).getBody();
    }

}
