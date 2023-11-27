package com.enqidev.ecommerce.controller;

import com.enqidev.ecommerce.dto.PaymentInfo;
import com.enqidev.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping(value = "/payment-intent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPaymentSession(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        Session sessionInfo = checkoutService.createPaymentSession(paymentInfo);

        String paymentStr = sessionInfo.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
