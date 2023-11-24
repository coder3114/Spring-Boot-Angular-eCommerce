package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(Long id) {
        final Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ResourceNotFoundException("No product found for this id");
        }
    }

    @Override
    public List<Product> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query);
        if (!products.isEmpty()) {
            return products;
        } else {
            throw new ResourceNotFoundException("No product found, please try another keyword.");
        }
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).get();
        existingProduct.setName(product.getName());
        Product updatedProduct = productRepository.save(existingProduct);
        return updatedProduct;
    }

    @Override
    public String deleteProduct(Long id) {
        boolean productExist = productRepository.findById(id).isPresent();
        if (productExist) {
            productRepository.deleteById(id);
            return "Deleted product with " + id;
        } else {
            throw new ResourceNotFoundException("No product found, cannot delete.");
        }
    }
}
