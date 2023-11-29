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

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart addToCart(Long productId, String userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Cart cart = new Cart(product, userId);

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCart(String userId) {

        List<Cart> carts = cartRepository.findByUserId(userId);

        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("No carts found for user with id: " + userId);
        }

        return carts;
    }

    @Override
    public Object removeFromCart(Long productId, String userId) {
        // returns the number of entities (rows) deleted by the query
        int deletedCount = cartRepository.deleteByProductIdAndUserId(productId, userId);

        if (deletedCount == 0) {
            throw new ResourceNotFoundException("No carts found for product with id: " + productId + " and user with id: " + userId);
        }

        return null;
    }

}
