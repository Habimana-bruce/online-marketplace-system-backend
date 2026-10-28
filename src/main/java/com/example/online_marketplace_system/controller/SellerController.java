package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.Seller;
import com.example.online_marketplace_system.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    @Autowired
    private SellerService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Seller seller) {
        String msg = service.save(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public List<Seller> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Seller> opt = service.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The seller with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Seller seller) {
        String msg = service.update(id, seller);
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
}
