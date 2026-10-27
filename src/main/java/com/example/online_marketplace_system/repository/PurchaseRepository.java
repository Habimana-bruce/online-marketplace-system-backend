package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository

public interface PurchaseRepository extends JpaRepository<Purchase, UUID>{

}

