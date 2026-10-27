package com.example.online_marketplace_system.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String shopName;
    private String ownerName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "storefront_id")
    private Storefront storefront;

    @ManyToMany
    @JoinTable(
            name = "seller_product_category",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    private List<ProductCategory> productCategories;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Storefront getStorefront() { return storefront; }
    public void setStorefront(Storefront storefront) { this.storefront = storefront; }
    public List<ProductCategory> getProductCategories() { return productCategories; }
    public void setProductCategories(List<ProductCategory> productCategories) { this.productCategories = productCategories; }
}