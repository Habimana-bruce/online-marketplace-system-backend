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
    private SellerService sellerService;

    @PutMapping("/{sellerId}/product-categories/{productCategoryId}")
    public ResponseEntity<String> addProductCategory(
            @PathVariable UUID sellerId, 
            @PathVariable UUID productCategoryId) {
        
        String msg = sellerService.addProductCategory(sellerId, productCategoryId);

        if (msg.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("The seller already has that product category")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/all")
    public List<Seller> all() {
        return sellerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Seller> opt = sellerService.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The seller with that id does not exist");
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Seller seller) {
        String msg = sellerService.save(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @PutMapping("/{sellerId}/storefronts/{storefrontId}")

public ResponseEntity<String> moveSellerToStorefront(

@PathVariable UUID sellerId,
@PathVariable UUID storefrontId){

String msg = sellerService.moveSellerToStorefront(sellerId, storefrontId);

if(msg.contains("does not exist")) {
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
}

if(msg.equals("Storefront is already occupied by another seller")) {

return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
}

return ResponseEntity.ok(msg);
}

}