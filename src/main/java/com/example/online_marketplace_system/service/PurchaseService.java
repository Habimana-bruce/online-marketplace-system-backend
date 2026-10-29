package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository repository;

    public String save(Purchase purchase) {
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
            purchase.setStatus(updated.getStatus());
            purchase.setProductName(updated.getProductName());
            purchase.setQuantity(updated.getQuantity());
            purchase.setBuyer(updated.getBuyer());
            purchase.setSeller(updated.getSeller());
            repository.save(purchase);
            return "Purchase updated successfully";
        }
        return "The purchase with that id does not exist";
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Purchase deleted successfully";
        }
        return "The purchase with that id does not exist";
    }


    public List<Purchase> getPurchasesByStatus(PurchaseStatus status) {

        return repository.findByStatusOrderByPurchaseDateAsc(status);
    }
}