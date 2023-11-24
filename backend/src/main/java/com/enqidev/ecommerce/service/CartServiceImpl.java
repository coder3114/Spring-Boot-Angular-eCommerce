package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.entity.User;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.CartRepository;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public CartServiceImpl(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(Long productId, String userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Cart cart = new Cart(product, user);
        return cartRepository.save(cart);
    }
}
