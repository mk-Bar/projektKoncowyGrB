package com.example.demo.domain.repository;

import com.example.demo.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepo extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductProductId(Long productId);
}
