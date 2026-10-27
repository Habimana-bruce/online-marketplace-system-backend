package com.example.online_marketplace_system.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "storefront")
public class Storefront {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int storefrontNumber;
    private String name;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getStorefrontNumber() { return storefrontNumber; }
    public void setStorefrontNumber(int storefrontNumber) { this.storefrontNumber = storefrontNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
