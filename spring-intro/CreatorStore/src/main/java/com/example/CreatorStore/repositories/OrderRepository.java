package com.example.CreatorStore.repositories;

import com.example.CreatorStore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
