package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product getProductById(Long id);

    //
    List<Product> searchProducts(String query);
//
//    Product updateProduct(Product product);
//
//    String deleteProduct(Long id);

}