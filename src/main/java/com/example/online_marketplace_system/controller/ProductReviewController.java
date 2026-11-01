package com.example.online_marketplace_system.controller;

import com.example.online_marketplace_system.domain.ProductReview;
import com.example.online_marketplace_system.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService service;

    @PostMapping("/save")
    public ResponseEntity<String> saveReview(
            @RequestParam("purchaseId") UUID purchaseId,
            @RequestBody ProductReview review) {
        String msg = service.saveReview(purchaseId, review);

        if (msg.equals("The purchase with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("Product reviews are only for DELIVERED purchases")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/buyer/{username}")
    public List<ProductReview> getBuyerReviews(@PathVariable String username) {
        return service.getReviewsByBuyerUsername(username);
    }
}