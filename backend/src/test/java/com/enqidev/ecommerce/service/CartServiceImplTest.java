package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.entity.User;
import com.enqidev.ecommerce.repository.CartRepository;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    @DisplayName("Should add the product to the cart when calling addToCart API")
    void testAddToCart() {
        Long productId = 1L;
        String userId = "testUser";
        Product mockProduct = new Product("TestProduct");
        User mockUser = new User("TestUser");
        Cart mockCart = new Cart(mockProduct, mockUser);

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(mockProduct));
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        Cart result = cartService.addToCart(productId, userId);

        verify(productRepository, times(1)).findById(productId);
        verify(userRepository, times(1)).findById(userId);
        verify(cartRepository, times(1)).save(any(Cart.class));

        assertEquals(mockCart, result);
        assertEquals(mockProduct, result.getProduct());
        assertEquals(mockUser, result.getUser());
    }

}
