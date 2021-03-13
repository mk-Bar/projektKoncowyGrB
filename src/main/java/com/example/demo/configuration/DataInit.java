package com.example.demo.configuration;

import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.ProductCategory;
import com.example.demo.domain.model.ProductType;
import com.example.demo.domain.model.Stock;
import com.example.demo.domain.repository.CategoryRepo;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.domain.repository.StockRepo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInit {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final StockRepo stockRepo;

    public DataInit(ProductRepo productRepo, CategoryRepo categoryRepo, StockRepo stockRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.stockRepo = stockRepo;
    }
    @PostConstruct  //po utworzeniu beana zostanie wywołana ta metoda
    public void init(){
        ProductCategory category = new ProductCategory();
        category.setType("typA");
        category.setName("kategoria1");
        category=categoryRepo.save(category);



        Product product = new Product();
        product.setProductName("produkt1");
        product.setProductType(ProductType.NEW);
        product.setProductCategory(category);
        product.setPrice(100.00);
        Stock stock = new Stock();
        stock.setQuantity(10);
        stock= stockRepo.save(stock);
        product.setStock(stock);
        product=productRepo.save(product);
    }
}
