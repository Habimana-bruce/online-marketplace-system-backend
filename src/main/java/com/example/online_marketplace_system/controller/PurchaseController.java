package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Purchase purchase) {
        String msg = service.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public List<Purchase> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable UUID id) {
        Optional<Purchase> purchase = service.getById(id);
        if (purchase.isPresent()) {
            return ResponseEntity.ok(purchase.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable UUID id,
            @RequestParam("status") PurchaseStatus status) {
        String msg = service.updateStatus(id, status);
        if (msg.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> getPurchasesStatsByStatus() {
        return service.getPurchasesStatsByStatus();
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(
            @RequestParam("sellerId") UUID sellerId,
            @RequestParam("date") LocalDate date) {
        String message = service.cancelPurchasesBySellerAndDate(sellerId, date);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/page")
    public Page<Purchase> getPurchasesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "purchaseDate,desc") String[] sort) {

        String sortBy = sort[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return service.getPurchasesPaginated(pageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);

        if (msg.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("That purchase has product reviews, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return ResponseEntity.ok(msg);
    }
}