package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.ProductCategory;
import com.example.online_marketplace_system.domain.Seller;
import com.example.online_marketplace_system.domain.Storefront;
import com.example.online_marketplace_system.repository.ProductCategoryRepository;
import com.example.online_marketplace_system.repository.SellerRepository;
import com.example.online_marketplace_system.repository.StorefrontRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private StorefrontRepository storefrontRepository;

    @Transactional
    public String addProductCategory(UUID sellerId, UUID productCategoryId) {
        Optional<Seller> sellerOpt = repository.findById(sellerId);
        if (sellerOpt.isEmpty()) {
            return "The seller with that id does not exist";
        }

        Optional<ProductCategory> categoryOpt = productCategoryRepository.findById(productCategoryId);
        if (categoryOpt.isEmpty()) {
            return "That product category does not exist";
        }

        Seller seller = sellerOpt.get();
        ProductCategory category = categoryOpt.get();

        if (seller.getProductCategories().contains(category)) {
            return "The seller already has that product category";
        }

        seller.getProductCategories().add(category);
        repository.save(seller);
        return "Product category added to the seller";
    }

    @Transactional
    public String moveSellerToStorefront(UUID sellerId, UUID storefrontId) {
        Optional<Seller> sellerOpt = repository.findById(sellerId);
        if (sellerOpt.isEmpty()) {
            return "The seller with that id does not exist";
        }

        Optional<Storefront> storefrontOpt = storefrontRepository.findById(storefrontId);
        if (storefrontOpt.isEmpty()) {
            return "That storefront does not exist";
        }

        Storefront storefront = storefrontOpt.get();

        boolean isOccupied = repository.existsByStorefrontId(storefrontId);
        if (isOccupied) {
            Seller currentOccupant = repository.findByStorefrontId(storefrontId).orElse(null);
            if (currentOccupant != null && !currentOccupant.getId().equals(sellerId)) {
                return "Storefront is already occupied by another seller";
            }
        }

        Seller seller = sellerOpt.get();
        seller.setStorefront(storefront);
        repository.save(seller);
        return "Seller moved to storefront successfully";
    }

    public String save(Seller seller) {
        repository.save(seller);
        return "Seller saved successfully";
    }

    public List<Seller> getAll() {
        return repository.findAll();
    }

    public Optional<Seller> getById(UUID id) {
        return repository.findById(id);
    }

    public String update(UUID id, Seller updated) {
        Optional<Seller> existing = repository.findById(id);
        if (existing.isPresent()) {
            Seller seller = existing.get();
            seller.setShopName(updated.getShopName());
            seller.setOwnerName(updated.getOwnerName());
            seller.setPhone(updated.getPhone());
            seller.setStorefront(updated.getStorefront());
            repository.save(seller);
            return "Seller updated successfully";
        }
        return "The seller with that id does not exist";
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Seller deleted successfully";
        }
        return "The seller with that id does not exist";
    }

    public List<Seller> getSellersByProductCategory(String name) {
        return repository.findByProductCategoryName(name);
    }
}