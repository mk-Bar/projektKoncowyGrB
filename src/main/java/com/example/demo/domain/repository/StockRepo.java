package com.example.demo.domain.repository;

import com.example.demo.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepo extends JpaRepository<Stock, Long> {
}
