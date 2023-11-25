package com.enqidev.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    private ProductCategory category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "active")
    private boolean active;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "preparation_time")
    private int preparationTime; // The time required to prepare the dish (in minutes)

    @Column(name = "is_vegetarian")
    private boolean isVegetarian;

    @Column(name = "is_vegan")
    private boolean isVegan;

    @Column(name = "is_gluten_free")
    private boolean isGlutenFree;

    @Column(name = "is_dairy_free")
    private boolean isDairyFree;

    @Column(name = "is_nut_free")
    private boolean isNutFree;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "meal_type")
    private String mealType;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }
}
