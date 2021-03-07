package com.example.demo.model;

import com.example.demo.domain.model.ProductCategory;
import com.example.demo.domain.model.ProductType;
import com.example.demo.domain.model.Stock;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
public class ProductDto {

    private Long productId;

    private String productName;
    private Double price;


    private ProductCategoryDto productCategory;

    private ProductType productType;

    private Integer quantity;

}
