package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Storefront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface StorefrontRepository extends JpaRepository<Storefront, UUID> {

}
