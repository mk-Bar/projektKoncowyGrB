package com.example.demo.controller;

import com.example.demo.domain.model.Product;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.model.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private final ProductService productService;

    //        dodawanie produktu
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    // wyswietlanie wszystkich produktó
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.showSavedProducts();
    }


}
