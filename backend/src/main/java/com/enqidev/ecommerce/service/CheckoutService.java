package com.enqidev.ecommerce.service;

import com.enqidev.ecommerce.dto.PaymentInfo;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface CheckoutService {

    Session createPaymentSession(PaymentInfo paymentInfo) throws StripeException;
}
