package com.example.demo.model;

import lombok.Data;


@Data
public class OrderLineForm {


    private Integer quantity;
    private Long productId;
}
