package com.example.demo.domain.repository;

import com.example.demo.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {


}
