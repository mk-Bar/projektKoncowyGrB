package com.example.demo.controller;

import com.example.demo.model.OrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderHtmlController {

    private final OrderService orderService;
    private final ProductService productService;

    public OrderHtmlController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    //    tworezenie formularza order
    @GetMapping("/orderForm")
    public String orderForm (Model model) {
        model.addAttribute("orderDto", new OrderDto());
        return "orderForm";
    }
    @PostMapping("/orderForm")
    public String newOrder(@ModelAttribute("orderDto") OrderDto orderDto) {
        Long orderId = orderService.createOrder(orderDto);
        return "redirect:/orderDetails/"+orderId;
    }
    @GetMapping("/orderDetails/{id}")
    public String orderDetails (Model model, @PathVariable ("id") Long id) {
        model.addAttribute("orderDto", orderService.showOrderById(id));
        model.addAttribute("listOfProducts",productService.showSavedProducts());
        return "orderDetails";
    }




//    utworzyc w bazie danyc  order
//    prekerwac uzytkowni ado  szczegóów zamowienia
//    wysietlanie zamwoeeni i listy produktow   guziki dodaj i podaj ilosc

}
