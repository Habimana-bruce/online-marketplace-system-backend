package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository

public interface PurchaseRepository extends JpaRepository<Purchase, UUID>{

    List<Purchase> findByStatusOrderByPurchaseDateAsc(PurchaseStatus status);

    List<Purchase> findByPurchaseDateBetweenOrderByPurchaseDateAsc(LocalDate start, LocalDate end);

  boolean existsBySellerIdAndPurchaseDateAndStatusNot(UUID sellerId, LocalDate purchaseDate, PurchaseStatus status);

    @Query("SELECT p.status, COUNT(p) FROM Purchase p GROUP BY p.status")
    List<Object[]> countPurchasesByStatus();

    @Modifying
    @Transactional
    @Query("UPDATE Purchase p SET p.status = 'CANCELLED' WHERE p.seller.id = :sellerId AND p.purchaseDate = :date AND p.status != 'DELIVERED'")
    int cancelPurchasesBySellerAndDate(@Param("sellerId") UUID sellerId, @Param("date") LocalDate date);

  long countByPurchaseDate(LocalDate purchaseDate);

  long countByStatus(PurchaseStatus status);
}

