package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.Buyer;
import com.example.online_marketplace_system.repository.BuyerRepository;
import com.example.online_marketplace_system.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepository repository;

    public String save(Buyer buyer) {
        repository.save(buyer);
        return "Buyer saved successfully";
    }

    public List<Buyer> getAll() {
        return repository.findAll();
    }

    public Optional<Buyer> getById(UUID id) {
        return repository.findById(id);
    }

    public String update(UUID id, Buyer updated) {
        Optional<Buyer> existing = repository.findById(id);
        if (existing.isPresent()) {
            Buyer buyer = existing.get();
            buyer.setFirstName(updated.getFirstName());
            buyer.setLastName(updated.getLastName());
            buyer.setUsername(updated.getUsername());
            buyer.setEmail(updated.getEmail());
            buyer.setPhone(updated.getPhone());
            repository.save(buyer);
            return "Buyer updated successfully";
        }
        return "The buyer with that id does not exist";
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Buyer deleted successfully";
        }
        return "The buyer with that id does not exist";
    }

    public List<Buyer> getBuyersByLastName(String lastName){
        return repository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
 @Autowired 
    private SellerRepository sellerRepository;

    public ResponseEntity<Object> getBuyersOfSeller(UUID sellerId){
        if (!sellerRepository.existsById(sellerId)) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND)
                    .body("the seller with that id does not exist");
        }
      List<Buyer> buyers = repository.findBuyersBySellerId(sellerId);
        return ResponseEntity.ok(buyers);

    }
}