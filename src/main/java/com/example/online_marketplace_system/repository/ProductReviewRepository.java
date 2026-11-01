package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {

    @Query("SELECT r FROM ProductReview r WHERE r.purchase.buyer.username = :username ORDER BY r.reviewDate DESC")
    List<ProductReview> findByBuyerUsernameOrdered(@Param("username") String username);

    boolean existsByPurchaseId(UUID purchaseId);
}