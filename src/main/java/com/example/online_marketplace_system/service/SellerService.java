package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.Seller;
import com.example.online_marketplace_system.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {
    @Autowired
    private SellerRepository repository;

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
}