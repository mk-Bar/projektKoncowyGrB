package com.example.demo.model;

import com.example.demo.domain.model.Order;
import com.example.demo.domain.model.Product;
import lombok.Data;


@Data
public class OrderLineDto {
    private Long orderLineId;

    private ProductDto product;

    private Double productUnitPrice;

    private Integer quantity;
}
