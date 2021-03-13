package com.example.demo.domain.repository;

import com.example.demo.domain.model.Order;
import com.example.demo.domain.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
