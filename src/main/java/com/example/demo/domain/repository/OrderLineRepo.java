package com.example.demo.domain.repository;

import com.example.demo.domain.model.OrderLine;
import com.example.demo.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
}
