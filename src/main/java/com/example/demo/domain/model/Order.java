package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordersPlaced")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    @ManyToOne   //do weryfikacji
    private User user;

//    private Double totalPrice;    //bedze wyliczona z orderLine

    private String userAddress;
    private String deliveryAddress;

    private LocalDate orderDate;

    @OneToMany (mappedBy = "idZamowienia")//do weryfikacji
    private List<OrderLine> orderLine;

    @Enumerated (EnumType.STRING)
    private Status status;


}
