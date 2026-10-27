package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository

public interface BuyerRepository extends JpaRepository<Buyer, UUID>{

}
