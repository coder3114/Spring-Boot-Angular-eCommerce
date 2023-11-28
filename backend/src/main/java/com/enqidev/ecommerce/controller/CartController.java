package com.enqidev.ecommerce.controller;


import com.enqidev.ecommerce.response.ResponseHandler;
import com.enqidev.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart/{productId}")
    public ResponseEntity addToCart(@PathVariable Long productId, @RequestBody String userId) {
        return ResponseHandler.responseBuilder("Details about the cart are given here",
                HttpStatus.OK, cartService.addToCart(productId, userId));
    }

    @GetMapping("/cart")
    public ResponseEntity<Object> getCart(@RequestParam String userId) {
        return ResponseHandler.responseBuilder("Details about the cart are given here",
                HttpStatus.OK, cartService.getCart(userId));
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity removeFromCart(@PathVariable Long productId, @RequestParam String userId) {

        String responseMessage = "Cart removed successfully. Details: userId is " + userId + " and userId is " + productId;

        return ResponseHandler.responseBuilder(responseMessage,
                HttpStatus.OK, cartService.removeFromCart(productId, userId));
    }
}