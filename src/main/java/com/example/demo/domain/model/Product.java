package com.example.demo.domain.model;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private Double price;

    @ManyToOne
    private ProductCategory productCategory;  // do wyjasnienia jak odniesc sie do kategorii


    @Enumerated (EnumType.STRING)
    private ProductType productType;

    @OneToOne
//    @OneToOne(mappedBy = "product")
    private Stock stock;
}
