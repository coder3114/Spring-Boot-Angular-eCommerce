package com.enqidev.ecommerce.repository;

import com.enqidev.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(String userId);

    // ensures the delete operation is atomic. If an exception occurs during the delete operation,
    // the transaction is rolled back, and the database remains in a consistent state
    @Transactional
    @Modifying // update or delete operation
    @Query("DELETE FROM Cart c WHERE c.product.id = :productId and c.userId = :userId")
    // custom JPQL (Java Persistence Query Language) query
    int deleteByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") String userId);
}
