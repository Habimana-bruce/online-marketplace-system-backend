package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.ProductReview;
import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.repository.ProductReviewRepository;
import com.example.online_marketplace_system.repository.PurchaseRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository reviewRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public String saveReview(UUID purchaseId, ProductReview review) {
        Optional<Purchase> purchaseOpt = purchaseRepository.findById(purchaseId);
        if (purchaseOpt.isEmpty()) {
            return "The purchase with that id does not exist";
        }

        Purchase purchase = purchaseOpt.get();
        if (purchase.getStatus() != PurchaseStatus.DELIVERED) {
            return "Product reviews are only for DELIVERED purchases";
        }

        review.setPurchase(purchase);
        review.setReviewDate(LocalDate.now());
        reviewRepository.save(review);
        return "Product review saved successfully";
    }
}