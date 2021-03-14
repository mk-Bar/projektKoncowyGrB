package com.example.demo.domain.repository;

import com.example.demo.domain.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<ProductCategory,Long> {

    boolean existsByName(String name);



}
