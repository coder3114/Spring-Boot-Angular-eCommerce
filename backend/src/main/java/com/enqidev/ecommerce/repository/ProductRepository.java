package com.enqidev.ecommerce.repository;

import com.enqidev.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product, Long> {

    //    Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :keyword, '%') OR p.description LIKE CONCAT('%', :keyword, '%') OR p.ingredients LIKE CONCAT('%', :keyword, '%')")
    List<Product> searchProducts(String keyword);
}
