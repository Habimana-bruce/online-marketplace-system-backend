package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.PurchaseStatus;
import com.example.online_marketplace_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


@Service public class DashboardService{

    @Autowired 
    private BuyerRepository buyerRepository;

    @Autowired 
    private SellerRepository sellerRepository;

    @Autowired 
    private StorefrontRepository storefrontRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired 
    private PurchaseRepository productReviewRepository;

    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("buyers", buyerRepository.count());

        summary.put("sellers", sellerRepository.count());

        summary.put("freeStorefronts", storefrontRepository.countFreeStorefronts());
        
        summary.put("purchasesToday", purchaseRepository.countByPurchaseDate(LocalDate.now()));
        
        summary.put("placed", purchaseRepository.countByStatus(PurchaseStatus.PLACED));
        
        summary.put("reviews", productReviewRepository.count());
        
        return summary;
    }
}