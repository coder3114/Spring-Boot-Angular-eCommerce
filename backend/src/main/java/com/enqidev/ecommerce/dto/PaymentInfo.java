package com.enqidev.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentInfo { // DTO (Data Transfer Object) transfer payment data between the server and client

    private double amount;
    private String currency;

    public PaymentInfo(String currency, double amount) {
        this.amount = amount;
        this.currency = currency;
    }
}
