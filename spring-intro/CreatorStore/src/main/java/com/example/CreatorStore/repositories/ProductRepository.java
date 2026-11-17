package com.example.CreatorStore.repositories;

import com.example.CreatorStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
