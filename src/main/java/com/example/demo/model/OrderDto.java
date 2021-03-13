package com.example.demo.model;

import com.example.demo.domain.model.OrderLine;
import com.example.demo.domain.model.Status;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {


    private Long orderNumber;
//    @OneToMany   //do weryfikacji
//    private String userName;
//    private Double totalPrice;    //bedze wyliczona z orderLine

    private String userAddress;
    private String deliveryAddress;
    private LocalDate orderDate;
    private List<OrderLineDto> orderLines;
    private Status status;
}
