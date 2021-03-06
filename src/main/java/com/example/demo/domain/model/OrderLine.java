package com.example.demo.domain.model;

import javax.persistence.*;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;

    @ManyToOne
    private Order idZamowienia;

    @ManyToOne
    private Product product;

    private Double productUitPrice;

    private Integer quantity;
}
