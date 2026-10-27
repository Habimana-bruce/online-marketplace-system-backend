package com.example.online_marketplace_system.repository;
import com.example.online_marketplace_system.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository

public interface SellerRepository extends JpaRepository<Seller, UUID>{

}
