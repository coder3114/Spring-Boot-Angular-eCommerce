package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.dto.PaymentInfo;
import com.enqidev.ecommerce.service.CheckoutService;
import com.enqidev.ecommerce.util.TestUtil;
import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @Test
    void testCreatePaymentIntent() throws Exception {

        PaymentInfo paymentInfo = new PaymentInfo("gbp", 100.0);
        paymentInfo.setAmount(100);
        paymentInfo.setCurrency("gbp");

        Session mockSession = new Session();
        mockSession.setId("mockSessionId");

        when(checkoutService.createPaymentSession(any(PaymentInfo.class)))
                .thenReturn(mockSession);

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertToJsonString(paymentInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("mockSessionId"));

        verify(checkoutService, times(1)).createPaymentSession(any(PaymentInfo.class));
    }
}
