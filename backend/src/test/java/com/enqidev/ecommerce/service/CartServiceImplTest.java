package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.entity.Cart;
import com.enqidev.ecommerce.entity.Product;
import com.enqidev.ecommerce.exception.ResourceNotFoundException;
import com.enqidev.ecommerce.repository.CartRepository;
import com.enqidev.ecommerce.repository.ProductRepository;
import com.enqidev.ecommerce.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// tells JUnit 5 to use the Mockito extension for test execution so @InjectMocks @Mock works as expected
public class CartServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    Long productId = 1L;
    String userId = "TestUser";
    Product mockProduct = TestUtil.getProductList().get(1);
    Cart mockCart = TestUtil.getCartList().get(1);

    @Test
    @DisplayName("Should add the product to the cart when calling addToCart API")
    void testAddToCart() {

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(mockProduct));
        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        final Cart result = cartService.addToCart(productId, userId);

        verify(productRepository, times(1)).findById(productId);
        verify(cartRepository, times(1)).save(any(Cart.class));

        assertEquals(mockCart, result);
        assertEquals(mockProduct, result.getProduct());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException with correct message if product is not found")
    void testAddToCartProductNotFound() {

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        final Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addToCart(productId, userId);
        });

        assertEquals("Product not found with id: " + productId, exception.getMessage());
    }

    @Test
    @DisplayName("Should get the user's cart")
    void testGetCart() {
        final String userId = "TestUser";
        final List<Cart> mockCartList = TestUtil.getCartList();

        when(cartRepository.findByUserId(userId)).thenReturn(mockCartList);

        final List<Cart> result = cartService.getCart(userId);

        verify(cartRepository, times(1)).findByUserId(userId);

        assertEquals(mockCartList, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException with correct message if user is not found")
    void testGetCartUserNotFound() {

        when(cartRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        final Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getCart(userId);
        });

        assertEquals("No carts found for user with id: " + userId, exception.getMessage());
    }

    @Test
    @DisplayName("Should remove the product from the cart")
    void testRemoveFromCart() {

        when(cartRepository.deleteByProductIdAndUserId(productId, userId)).thenReturn(1);

        Object result = cartService.removeFromCart(productId, userId);

        verify(cartRepository, times(1)).deleteByProductIdAndUserId(productId, userId);

        assertEquals(null, result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when trying to remove a non-existing product from the cart")
    void testRemoveFromCartProductNotFound() {

        when(cartRepository.deleteByProductIdAndUserId(productId, userId)).thenReturn(0);

        // Call the service and expect an exception
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.removeFromCart(productId, userId);
        });

        verify(cartRepository, times(1)).deleteByProductIdAndUserId(productId, userId);

        String expectedMessage = "No carts found for product with id: " + productId + " and user with id: " + userId;
        String actualMessage = exception.getMessage();
        assert (actualMessage.contains(expectedMessage));
    }
}
