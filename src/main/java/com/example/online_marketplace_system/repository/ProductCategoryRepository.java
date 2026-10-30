package com.example.online_marketplace_system.repository;

import com.example.online_marketplace_system.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

  @Query("SELECT c FROM ProductCategory c WHERE c.sellers IS EMPTY")
  List<ProductCategory> findUnusedProductCategories();  
}