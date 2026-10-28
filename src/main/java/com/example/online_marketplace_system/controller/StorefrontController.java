package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.Storefront;
import com.example.online_marketplace_system.service.StorefrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/storefronts")
public class StorefrontController {
    @Autowired
    private StorefrontService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Storefront storefront) {
        String msg = service.save(storefront);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public List<Storefront> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Storefront> opt = service.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The storefront with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Storefront storefront) {
        String msg = service.update(id, storefront);
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