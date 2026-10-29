
package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.Purchase;
import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

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
    public List<Purchase> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Purchase> opt = service.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The purchase with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Purchase purchase) {
        String msg = service.update(id, purchase);
        if (msg.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {

        String msg = service.delete(id);

        if (msg.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/by-status")

    public List<Purchase> getByStatus(@RequestParam("status") PurchaseStatus status) {

        return service.getPurchasesByStatus(status);

    }

    @GetMapping("/between")
    public List<Purchase> getBetween(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {
        return service.getPurchasesBetweenDates(start, end);
    }

    }