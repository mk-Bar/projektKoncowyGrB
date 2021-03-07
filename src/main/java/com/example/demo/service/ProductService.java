package com.example.demo.service;

import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.ProductCategory;
import com.example.demo.domain.model.Stock;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.domain.repository.StockRepo;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.model.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final StockRepo stockRepo;
    private final CategoryService categoryService;

    public ProductService(ProductRepo productRepo, StockRepo stockRepo, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.stockRepo = stockRepo;
        this.categoryService = categoryService;
    }


    //    tworzernie
    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setProductType(productDto.getProductType());
        ProductCategory category = categoryService.getById(productDto.getProductCategory().getId());
        product.setProductCategory(category);
        Stock stock = new Stock();
        stock.setQuantity(productDto.getQuantity());
        Stock savedInDb = stockRepo.save(stock);
        product.setStock(savedInDb);
        return productRepo.save(product);
    }

//    wyswietlanie

    public List<ProductDto> showSavedProducts() {

        return productRepo.findAll().stream().map(this::map).collect(Collectors.toList()); //dla frontendu
    }

    private ProductDto map(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setProductId(product.getProductId());
        productDto.setProductType(product.getProductType());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getStock().getQuantity());
        productDto.setProductCategory(categoryService.map(product.getProductCategory()));
        return productDto;
    }
//    update

//    usuwanie


    }
