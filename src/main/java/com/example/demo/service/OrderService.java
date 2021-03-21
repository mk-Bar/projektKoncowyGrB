package com.example.demo.service;

import com.example.demo.domain.model.*;
import com.example.demo.domain.repository.OrderLineRepo;
import com.example.demo.domain.repository.OrderRepo;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.exceptions.ApplicationException;
import com.example.demo.exceptions.InsufficienteStockException;
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
    private final UserService userService;


    public OrderService(ProductService productService, ProductRepo productRepo, OrderLineRepo orderLineRepo, OrderRepo orderRepo, UserService userService) {
        this.productService = productService;
        this.productRepo = productRepo;
        this.orderLineRepo = orderLineRepo;
        this.orderRepo = orderRepo;
        this.userService = userService;
    }

    // TODO: 21.03.2021 realacje miedzy userem a orderem
    //dodawanmie lini zamowienia
    public Long createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(userService.findUserEntity());//do weryfikacji
        order.setOrderDate(LocalDate.now());
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setUserAddress(orderDto.getUserAddress());
        order.setStatus(Status.AWAITING_PAYMENT);
        Order savedInDb = orderRepo.save(order);


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

    public void addOrderLineToOrder(Long orderId, OrderLineForm orderLineForm) throws InsufficienteStockException {
        OrderLineDto orderLineDto = new OrderLineDto();
        Integer stockQuantity = productService.getProductQuantity(productRepo.findById(orderLineForm.getProductId()).orElseThrow(()-> new ApplicationException("Product with provided ID does not exists")));
        if (orderLineForm.getQuantity() <= stockQuantity) {
            orderLineDto.setQuantity(orderLineForm.getQuantity());
Integer orderedQuantity=orderLineForm.getQuantity();
            ProductDto productDto = new ProductDto();
            productDto.setProductId(orderLineForm.getProductId());
            orderLineDto.setProduct(productDto);
            addOrderLineToOrder(orderId, orderLineDto);

            productService.changeStock(-1*orderedQuantity,orderLineForm.getProductId());

        }else{
            throw new InsufficienteStockException();
        }
    }
//    wyslietlanie order po id

    public OrderDto showOrderById(Long orderId) {

        return orderRepo.findById(orderId).map(this::map).
                orElseThrow(() -> new ApplicationException("Order with provided ID does not exists"));
    }

    private OrderDto map(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(order.getUser());
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
            orderLinedto.setOrderLineId(orderLine.getOrderLineId());
            return orderLinedto;
        }).collect(Collectors.toList()));

        return orderDto;
    }

    //    usuwanie lini zamowienia
    public void deleteOrderLine(Long id) {

        OrderLine orderLine=orderLineRepo.findById(id).orElseThrow(()-> new ApplicationException("No such orderLine exists"));
        productService.changeStock(orderLine.getQuantity(),orderLine.getProduct().getProductId());
        orderLineRepo.deleteById(id);
    }

    public List<OrderDto> getOrders(){
        return userService.findUserEntity().getOrders().stream().map(this::map).collect(Collectors.toList());
    }

}

