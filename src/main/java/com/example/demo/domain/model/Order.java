package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

//    @OneToMany   //do weryfikacji
//    private String userName;

//    private Double totalPrice;    //bedze wyliczona z orderLine

    private String userSddress;
    private String deliveryAddress;

    private LocalDate orderDate;

    @OneToMany //do weryfikacji
    private List<OrderLine> orderLine;

    @Enumerated (EnumType.STRING)
    private Status status;


}
