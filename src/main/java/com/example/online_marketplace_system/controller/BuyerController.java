package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.Buyer;
import com.example.online_marketplace_system.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {
    @Autowired
    private BuyerService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Buyer buyer) {
        String msg = service.save(buyer);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public List<Buyer> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Buyer> opt = service.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The buyer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Buyer buyer) {
        String msg = service.update(id, buyer);
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

    @GetMapping("/by-last-name")
    public List<Buyer> getByLastName(@RequestParam("lastName") String lastName) {
        return service.getBuyersByLastName(lastName);
    }

}