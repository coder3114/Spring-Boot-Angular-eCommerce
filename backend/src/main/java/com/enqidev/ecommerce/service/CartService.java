package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addToCart(Long productId, String userId);

    List<Cart> getCart(String userId);

    void removeFromCart(Long productId);

}
