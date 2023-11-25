package com.enqidev.ecommerce.controller;


import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId, @RequestBody String userId) {
        try {
            Cart cart = cartService.addToCart(productId, userId);
            return ResponseEntity.ok(cart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
