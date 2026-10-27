package com.example.online_marketplace_system.service;
import com.example.online_marketplace_system.domain.StoreFront;
import com.example.online_marketplace_system.domain.Storefront;
import com.example.online_marketplace_system.repository.StoreFrontRepository;
import org.springFrameWork.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotypes.service;

import java.util.List;
import java.util.list;
import java.util.Optional;
import java.util.UUID;

 @Service

 public class StorefrontService {
    @Autowired
    private StoreFrontRepository repository;

    public string save(Storefront storefront){
        repository.save(storefront);
        return "Storefront saved Successfully";
    }
    public List<Storefront> getAll() {
        return repository.findAll(id);
    }

}
