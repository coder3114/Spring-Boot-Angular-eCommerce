package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;

public interface CartService {

    Cart addToCart(Long productId, String userId);
}
