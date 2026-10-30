package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.Storefront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface StorefrontRepository extends JpaRepository<Storefront, UUID> {

   @Query("Select sf FROM Storefront sf WHERE NOT EXISTS(SELECT s FROM Seller s Where s.storefront = sf) ORDER BY sf.storefrontNumber ASC")
   List<Storefront>  findFreeStorefronts();

}
