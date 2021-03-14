package com.example.demo.controller;

import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderLineDto;
import com.example.demo.model.ProductDto;
import com.example.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//jeden kontroller
//        jeden request z ktorego stworzy sie cale zamowienie razem z orderline
//        w requescie w body bedzie lista orderlinow
//        prezkazac do serwisu, kory ma podpiac  to do ordera i wyswietlic wynik


@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    //        dodawanie zamowienia
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDto order) {
        orderService.createOrder(order);
    }

    //    dodawanie  orderline do zamowienia
    @PutMapping("/{orderId}")
    public void addOrderLine(@PathVariable Long orderId,@RequestBody OrderLineDto orderLine) {
        orderService.addOrderLineToOrder(orderId,orderLine);
    }




    //    wyswietl zamowienie  po id
    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.showOrderById(orderId);
    }

//    usuwanie orderLine po id
@DeleteMapping("/{orderLineId}")

public void deleteorderLine(@PathVariable Long orderLineId) {
    orderService.deleteOrderLine(orderLineId);
}
}


