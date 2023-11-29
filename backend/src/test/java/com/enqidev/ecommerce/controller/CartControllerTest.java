package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.service.CartService;
import com.enqidev.ecommerce.util.TestUtil;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc; // simulates HTTP requests and responses without a running server

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
        this.mockCart = TestUtil.getCartList().get(0);
        this.mockCart1 = TestUtil.getCartList().get(1);
        this.cartList = TestUtil.getCartList();
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
                .andExpect(jsonPath("$.data.product.name").value("TestProduct")) // $ symbol represents the root of the JSON document
                .andExpect(jsonPath("$.data.userId").value("TestUser"))
                .andExpect(jsonPath("message").value("Details about the cart are given here"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addToCart(productId, userId);
    }

    @Test
    @DisplayName("Should handle exception and return INTERNAL_SERVER_ERROR")
    void testAddToCartProductNotFound() throws Exception {

        when(cartService.addToCart(productId, userId)).thenThrow(new ResourceNotFoundException("Cannot add to cart."));

        mockMvc.perform(post("/api/addToCart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userId))
                .andExpect(jsonPath("message").value("Cannot add to cart."))
                .andExpect(status().isNotFound());

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
                .andExpect(jsonPath("$.data[0].product.name").value("TestProduct"))
                .andExpect(jsonPath("$.data[1].product.name").value("TestProduct1"))
                .andExpect(jsonPath("$.data[0].userId").value("TestUser"))
                .andExpect(jsonPath("message").value("Details about the cart are given here"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    @DisplayName("Should handle exception if cannot find the cart for the user")
    void testGetCartUserNotFound() throws Exception {

        when(cartService.getCart(userId)).thenThrow(new ResourceNotFoundException("No carts found."));

        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId))
                .andExpect(jsonPath("message").value("No carts found."))
                .andExpect(status().isNotFound());

        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    @DisplayName("Should remove the product from the cart")
    void testRemoveFromCart() throws Exception {
        when(cartService.removeFromCart(productId, userId)).thenReturn(null);

        mockMvc.perform(delete("/api/cart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId))
                .andExpect(jsonPath("message").value("Cart removed successfully. Details: userId is " + userId + " and userId is " + productId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).removeFromCart(productId, userId);
    }

    @Test
    @DisplayName("Should handle exception and return INTERNAL_SERVER_ERROR")
    void testRemoveFromCartProductNotFound() throws Exception {
        when(cartService.removeFromCart(productId, userId)).thenThrow(new ResourceNotFoundException("Cannot remove from cart."));

        mockMvc.perform(delete("/api/cart/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId))
                .andExpect(jsonPath("message").value("Cannot remove from cart."))
                .andExpect(status().isNotFound());

        verify(cartService, times(1)).removeFromCart(productId, userId);
    }

}
