package com.example.demo.model;

import com.example.demo.domain.model.ProductType;
import lombok.Data;

@Data
public class ProductFormDto {

    private Long productId;

    private String productName;
    private Double price;
    private Long productCategoryId;
    private ProductType productType;
    private Integer quantity;

}
