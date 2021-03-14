package com.example.demo.domain.repository;

import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByProductCategory(ProductCategory category);

}
