package com.example.demo.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;

    @ManyToOne
    private Order idZamowienia;

    @ManyToOne
    private Product product;

    private Double productUnitPrice;

    private Integer quantity;
}
