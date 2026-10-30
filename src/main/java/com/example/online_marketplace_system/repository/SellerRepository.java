package com.example.online_marketplace_system.repository;
import com.example.online_marketplace_system.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository

public interface SellerRepository extends JpaRepository<Seller, UUID>{

    @Query("SELECT s FROM Seller s JOIN s.productCategories c WHERE LOWER(c.name) = LOWER(name)")
    List<Seller> findByProductCategoryName(@Param("name")String name);

    
}
