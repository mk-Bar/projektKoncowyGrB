package com.example.demo.service;

import com.example.demo.domain.model.*;
import com.example.demo.domain.repository.OrderLineRepo;
import com.example.demo.domain.repository.OrderRepo;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.exceptions.ApplicationException;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderLineDto;
import com.example.demo.model.OrderLineForm;
import com.example.demo.model.ProductDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductService productService;
    private final ProductRepo productRepo;
    private final OrderLineRepo orderLineRepo;
    private final OrderRepo orderRepo;

    public OrderService(ProductService productService, ProductRepo productRepo, OrderLineRepo orderLineRepo, OrderRepo orderRepo) {
        this.productService = productService;
        this.productRepo = productRepo;
        this.orderLineRepo = orderLineRepo;
        this.orderRepo = orderRepo;
    }

    //dodawanmie lini zamowienia
    public Long createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setUserAddress(orderDto.getUserAddress());
        order.setStatus(Status.AWAITING_PAYMENT);
        Order savedInDb = orderRepo.save(order);

        // TODO: 18.03.2021  sprawdzeic czemu nei działą
        if (orderDto.getOrderLines() != null) {
            orderDto.getOrderLines().stream().forEach(orderLineDto -> {
                OrderLine orderLine = new OrderLine();
                orderLine.setQuantity(orderLineDto.getQuantity());
                Product product = productRepo.findById(orderLineDto.getProduct().getProductId()).orElseThrow(() -> new ApplicationException("No such product exists"));
                orderLine.setProduct(product);
                orderLine.setProductUnitPrice(product.getPrice());
                orderLine.setIdZamowienia(savedInDb);
                orderLineRepo.save(orderLine);
            });
        }
        return savedInDb.getOrderNumber();
    }

    //w kontrolerze  w body : orderlineDto
//    w parametrze bedzei orderID      pathvariable
    public void addOrderLineToOrder(Long orderId, OrderLineDto orderLineDto) {
        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(orderLineDto.getQuantity());
        Product product = productRepo.findById(orderLineDto.getProduct().getProductId()).orElseThrow(() -> new ApplicationException("No such product exists"));
        orderLine.setProduct(product);
        orderLine.setProductUnitPrice(product.getPrice());
        orderLine.setIdZamowienia(orderRepo.findById(orderId).orElseThrow(() -> new ApplicationException("Order with provided ID does not exists")));
        orderLineRepo.save(orderLine);
    }

    public void addOrderLineToOrder(Long orderId, OrderLineForm orderLineForm) {
        OrderLineDto orderLineDto = new OrderLineDto();
        orderLineDto.setQuantity(orderLineForm.getQuantity());
        ProductDto productDto = new ProductDto();
        productDto.setProductId(orderLineForm.getProductId());
        orderLineDto.setProduct(productDto);
        addOrderLineToOrder(orderId, orderLineDto);
    }
//    wyslietlanie order po id

    public OrderDto showOrderById(Long orderId) {

        return orderRepo.findById(orderId).map(this::map).
                orElseThrow(() -> new ApplicationException("Order with provided ID does not exists"));
    }

    private OrderDto map(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserAddress(order.getUserAddress());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());
        orderDto.setDeliveryAddress(order.getDeliveryAddress());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderLines(order.getOrderLine().stream().map(orderLine -> {
            OrderLineDto orderLinedto = new OrderLineDto();
            ProductDto productDto = new ProductDto();
            productDto.setProductName(orderLine.getProduct().getProductName());
            productDto.setProductType(orderLine.getProduct().getProductType());
            productDto.setProductId(orderLine.getProduct().getProductId());
            productDto.setPrice(orderLine.getProduct().getPrice());

            orderLinedto.setQuantity(orderLine.getQuantity());
            orderLinedto.setProduct(productDto);
            return orderLinedto;
        }).collect(Collectors.toList()));

        return orderDto;
    }

    //    usuwanie lini zamowienia
    public void deleteOrderLine(Long id) {
        if (!orderLineRepo.existsById(id)) {
            throw new ApplicationException("No such orderLine exists");
        }
        orderLineRepo.deleteById(id);
    }

}

