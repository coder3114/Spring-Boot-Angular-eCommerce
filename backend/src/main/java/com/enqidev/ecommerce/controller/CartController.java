package com.enqidev.ecommerce.controller;


import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/addToCart/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId, @RequestBody String userId) {
        try {
            Cart cart = cartService.addToCart(productId, userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCart(@RequestParam String userId) {
        try {
            List<Cart> cart = cartService.getCart(userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId) {
        try {
            cartService.removeFromCart(productId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}