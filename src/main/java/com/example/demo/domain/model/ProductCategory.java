package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

//    public ProductCategory(String name) {
//        this.name = name;
//    }
//
//    public ProductCategory() {
//
//    }
}
