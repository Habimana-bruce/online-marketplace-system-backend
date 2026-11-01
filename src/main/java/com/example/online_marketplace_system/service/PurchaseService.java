package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.repository.ProductReviewRepository;
import com.example.online_marketplace_system.repository.PurchaseRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    public String delete(UUID id) {
        if (!repository.existsById(id)) {
            return "The purchase with that id does not exist";
        }

        if (productReviewRepository.existsByPurchaseId(id)) {
            return "That purchase has product reviews, delete them first";
        }

        repository.deleteById(id);
        return "Purchase deleted successfully";
    }

    @Transactional
    public String save(Purchase purchase) {
        if (purchase.getPurchaseDate() != null && purchase.getPurchaseDate().isBefore(LocalDate.now())) {
            return "Purchase date cannot be in the past";
        }

        if (purchase.getSeller() != null && purchase.getPurchaseDate() != null) {
            boolean exists = repository.existsBySellerIdAndPurchaseDateAndStatusNot(
                    purchase.getSeller().getId(),
                    purchase.getPurchaseDate(),
                    PurchaseStatus.CANCELLED
            );
            if (exists) {
                return "Seller already has a purchase on that date";
            }
        }

        purchase.setStatus(PurchaseStatus.PLACED);

        repository.save(purchase);
        return "Purchase saved successfully";
    }

    public List<Purchase> getAll() {
        return repository.findAll();
    }

    public Optional<Purchase> getById(UUID id) {
        return repository.findById(id);
    }

    public String update(UUID id, Purchase updated) {
        Optional<Purchase> existing = repository.findById(id);
        if (existing.isPresent()) {
            Purchase purchase = existing.get();
            purchase.setPurchaseDate(updated.getPurchaseDate());
            purchase.setProductName(updated.getProductName());
            purchase.setQuantity(updated.getQuantity());
            purchase.setBuyer(updated.getBuyer());
            purchase.setSeller(updated.getSeller());
            repository.save(purchase);
            return "Purchase updated successfully";
        }
        return "The purchase with that id does not exist";
    }

    @Transactional
    public String updateStatus(UUID id, PurchaseStatus newStatus) {
        Optional<Purchase> existing = repository.findById(id);
        if (!existing.isPresent()) {
            return "The purchase with that id does not exist";
        }

        Purchase purchase = existing.get();
        PurchaseStatus current = purchase.getStatus();
        boolean isValid = false;

        if (current == PurchaseStatus.PLACED) {
            if (newStatus == PurchaseStatus.SHIPPED || newStatus == PurchaseStatus.CANCELLED) {
                isValid = true;
            }
        } else if (current == PurchaseStatus.SHIPPED) {
            if (newStatus == PurchaseStatus.DELIVERED || newStatus == PurchaseStatus.CANCELLED) {
                isValid = true;
            }
        } else if (current == PurchaseStatus.DELIVERED || current == PurchaseStatus.CANCELLED) {
            isValid = false;
        }

        if (!isValid) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        purchase.setStatus(newStatus);
        repository.save(purchase);
        return "Purchase status updated successfully";
    }



    public List<Purchase> getPurchasesByStatus(PurchaseStatus status) {
        return repository.findByStatusOrderByPurchaseDateAsc(status);
    }

    public List<Purchase> getPurchasesBetweenDates(LocalDate start, LocalDate end) {
        return repository.findByPurchaseDateBetweenOrderByPurchaseDateAsc(start, end);
    }

    public List<Object[]> getPurchasesStatsByStatus() {
        return repository.countPurchasesByStatus();
    }

    @Transactional
    public String cancelPurchasesBySellerAndDate(UUID sellerId, LocalDate date) {
        int updatedCount = repository.cancelPurchasesBySellerAndDate(sellerId, date);
        return updatedCount + " purchases cancelled";
    }

    public Page<Purchase> getPurchasesPaginated(Pageable pageable){

        return repository.findAll(pageable);
    }

}