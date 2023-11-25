package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.CartRepository;
import com.enqidev.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(Long productId, String userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        Cart cart = new Cart(product, userId);
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCart(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("No carts found for user with ID: " + userId);
        }
        return carts;
    }


}
