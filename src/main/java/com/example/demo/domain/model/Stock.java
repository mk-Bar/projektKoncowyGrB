package com.example.demo.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(mappedBy = "stock")
   private Product product;

    private Integer quantity;


}
