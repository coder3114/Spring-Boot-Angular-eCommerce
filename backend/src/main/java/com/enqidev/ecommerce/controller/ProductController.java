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

    private final ProductService productService;

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
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED).getBody();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable("keyword") String keyword) {
        try {
            return ResponseEntity.ok(productService.searchProducts(keyword));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/products/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
//        return ResponseEntity.ok(productService.updateProduct(productDetails));
//    }
//
//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
//        try {
//            return ResponseEntity.ok(productService.deleteProduct(id));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
