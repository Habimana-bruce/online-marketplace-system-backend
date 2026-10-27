package com.example.online_marketplace_system.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private String productName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public PurchaseStatus getStatus() { return status; }
    public void setStatus(PurchaseStatus status) { this.status = status; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Buyer getBuyer() { return buyer; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }
    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
}