package com.enqidev.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // JPA Entity, allow Java objects to be mapped to relational database tables
@Table(name = "cart")
@Data // projectLombok auto generate getter/setter methods etc
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    // each record in the first table (in this case, the Cart table) is associated with exactly one record in the second table (the Product table)
    @JoinColumn(name = "product_id")
    // specify the name of the foreign key column in the Cart table that references the primary key column in the Product table
    private Product product;

    private String userId;

    public Cart() {
    }

    public Cart(Product product, String userId) {
        this.product = product;
        this.userId = userId;
    }
}
