package com.example.demo.controller;

import com.example.demo.exceptions.InsufficienteStockException;
import com.example.demo.exceptions.ProductUseCategoryException;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderLineDto;
import com.example.demo.model.OrderLineForm;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String orderForm(Model model) {
        model.addAttribute("orderDto", new OrderDto());
        return "orderForm";
    }

    @PostMapping("/orderForm")
    public String newOrder(@ModelAttribute("orderDto") OrderDto orderDto) {
        Long orderId = orderService.createOrder(orderDto);
        return "redirect:/orderDetails/" + orderId;
    }

    @GetMapping("/orderDetails/{id}")
    public String orderDetails(Model model, @PathVariable("id") Long id, @RequestParam(value = "stockError", required = false) String stockError) {
        if (stockError != null) {
            model.addAttribute("stockError", true);
        }

        model.addAttribute("orderDto", orderService.showOrderById(id));
        model.addAttribute("listOfProducts", productService.showSavedProducts());
        model.addAttribute("orderLineForm", new OrderLineForm());

        return "orderDetails";
    }

    @GetMapping("/orders")
    public String orderList(Model model) {
        model.addAttribute("orderList", orderService.getOrders());

        return "orders";
    }

    @PostMapping("/orderDetails/{id}")
    public String addOrderLine(@PathVariable("id") Long id, @ModelAttribute("orderLineForm") OrderLineForm orderLineForm) {
        try {
            orderService.addOrderLineToOrder(id, orderLineForm);
            return "redirect:/orderDetails/" + id;
        } catch (InsufficienteStockException e) {
            return "redirect:/orderDetails/" + id + "?stockError=true";
        }

    }


//    utworzyc w bazie danyc  order
//    prekerwac uzytkowni ado  szczegóów zamowienia
//    wysietlanie zamwoeeni i listy produktow   guziki dodaj i podaj ilosc

}
