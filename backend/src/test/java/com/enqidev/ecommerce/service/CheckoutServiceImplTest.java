package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.dto.PaymentInfo;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CheckoutServiceImplTest {

    @Mock
    private Session mockSession;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    final String currency = "gbp";

    final long amount = 100L;

    @Test
    @DisplayName("Should create payment session successfully")
    void testCreatePaymentSession() throws StripeException {

        // Initialize CheckoutService
        checkoutService = new CheckoutServiceImpl("sk_test_51O72RLBgJydn0KVfpiDkjAAiONGXbU1z4r0HAHhbItxUrq43QnZmVWhZXWxtlahvvUspORI3NxoqkOb1xRYdHJFR00RdiiIiu1");

        // Arrange
        PaymentInfo paymentInfo = new PaymentInfo(currency, amount);

        // Act
        Session result = checkoutService.createPaymentSession(paymentInfo);

        // Assert
        assertEquals(currency, result.getCurrency());
        assertEquals(amount * 100, result.getAmountTotal());
        assertEquals(amount * 100, result.getAmountSubtotal());
        assertNotNull(result.getId());
    }
/*
    @Test
    @DisplayName("Should handle StripeException")
    void testCreatePaymentSessionWithStripeException() {

        PaymentInfo paymentInfo = new PaymentInfo(currency, amount);

        when(mockProduct.getDefaultPrice()).thenThrow(new ApiConnectionException("example error"));

        assertThrows(ResourceNotFoundException.class, () -> checkoutService.createPaymentSession(paymentInfo));
    }*/
}
