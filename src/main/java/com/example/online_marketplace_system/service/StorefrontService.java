package com.example.online_marketplace_system.service;

import com.example.online_marketplace_system.domain.Storefront;
import com.example.online_marketplace_system.repository.StorefrontRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorefrontService {
    @Autowired
    private StorefrontRepository repository;



    public String save(Storefront storefront) {


        repository.save(storefront);
        return "Storefront saved successfully";
    }

    public List<Storefront> getAll() {
        return repository.findAll();
    }

    public Optional<Storefront> getById(UUID id) {
        return repository.findById(id);
    }

    public String update(UUID id, Storefront updated) {
        Optional<Storefront> existing = repository.findById(id);

        if (existing.isPresent()) {
            Storefront storefront = existing.get();
            storefront.setStorefrontNumber(updated.getStorefrontNumber());
            storefront.setName(updated.getName());
            repository.save(storefront);
            return "Storefront updated successfully";
        }
        return "The storefront with that id does not exist";
    }

    public String delete(UUID id) {

           if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Storefront deleted successfully";
        }
        return "The storefront with that id does not exist";
    }

    public List<Storefront>  getFreeStorefronts(){

        return repository.findFreeStorefronts();
    }
}