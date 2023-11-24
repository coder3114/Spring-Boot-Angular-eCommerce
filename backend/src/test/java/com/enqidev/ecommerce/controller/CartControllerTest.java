package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.entity.User;
import com.enqidev.ecommerce.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("Should add the product to the cart when calling addToCart API")
    void testAddToCart() throws Exception {

        Long productId = 1L;
        String userId = "testUser";
        Cart mockCart = new Cart(new Product("TestProduct"), new User("TestUser"));

        when(cartService.addToCart(productId, userId)).thenReturn(mockCart);

        mockMvc.perform(post("/api/addToCart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userId))
                .andExpect(jsonPath("$.product.name").value("TestProduct"))
                .andExpect(jsonPath("$.user.username").value("TestUser"));

        verify(cartService, times(1)).addToCart(productId, userId);
    }
}
