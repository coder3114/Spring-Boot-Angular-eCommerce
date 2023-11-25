package com.enqidev.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JoinColumn(name = "user_id")
    private String userId;

    public Cart(Product product, String userId) {
        this.product = product;
        this.userId = userId;
    }
}
