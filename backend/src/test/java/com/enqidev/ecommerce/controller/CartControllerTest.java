package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.service.CartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private Long productId;
    private String userId;
    private Cart mockCart;
    private Cart mockCart1;
    private List<Cart> cartList;

    @BeforeEach
    void setUp() {
        this.productId = 1L;
        this.userId = "TestUser";
        this.mockCart = new Cart(new Product("TestProduct"), userId);
        this.mockCart1 = new Cart(new Product("TestProduct1"), userId);
        this.cartList = Arrays.asList(mockCart, mockCart1);
    }

    @AfterEach
    void tearDown() {
        this.productId = null;
        this.userId = null;
        this.mockCart = null;
        this.mockCart1 = null;
        this.cartList = null;
    }

    @Test
    @DisplayName("Should add the product to the cart when calling addToCart API")
    void testAddToCart() throws Exception {

        when(cartService.addToCart(productId, userId)).thenReturn(mockCart);

        mockMvc.perform(post("/api/addToCart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userId))
                .andExpect(jsonPath("$.product.name").value("TestProduct"))
                .andExpect(jsonPath("$.userId").value("TestUser"));

        verify(cartService, times(1)).addToCart(productId, userId);
    }

    @Test
    @DisplayName("Should handle exception and return INTERNAL_SERVER_ERROR")
    void testAddToCartProductNotFound() throws Exception {

        when(cartService.addToCart(productId, userId)).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/addToCart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userId))
                .andExpect(status().isInternalServerError());

        verify(cartService, times(1)).addToCart(productId, userId);
    }

    @Test
    @DisplayName("Should get the user's cart")
    void testGetCart() throws Exception {

        when(cartService.getCart(userId)).thenReturn(cartList);

        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.name").value("TestProduct"))
                .andExpect(jsonPath("$[1].product.name").value("TestProduct1"))
                .andExpect(jsonPath("$[0].userId").value("TestUser"));

        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    @DisplayName("Should handle exception and return INTERNAL_SERVER_ERROR")
    void testGetCartUserNotFound() throws Exception {

        when(cartService.getCart(userId)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId))
                .andExpect(status().isInternalServerError());

        verify(cartService, times(1)).getCart(userId);
    }

}
