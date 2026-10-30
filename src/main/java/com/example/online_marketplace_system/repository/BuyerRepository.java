package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {


    List<Buyer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT p.buyer FROM Purchase p WHERE p.seller.id = :sellerId")
    
    List<Buyer> findBuyersBySellerId(
            @Param("sellerId")UUID sellerId
    );
}
