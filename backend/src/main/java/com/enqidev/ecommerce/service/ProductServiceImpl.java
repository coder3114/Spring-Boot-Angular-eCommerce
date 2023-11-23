package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

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
            throw new ResourceNotFoundException("Errorrrr");
        }
    }

}
