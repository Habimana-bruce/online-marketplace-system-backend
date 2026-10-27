package com.example.online_marketplace_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "productCategories")
    private List<Seller> sellers;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Seller> getSellers() { return sellers; }
    public void setSellers(List<Seller> sellers) { this.sellers = sellers; }
}
