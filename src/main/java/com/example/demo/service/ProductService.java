package com.example.demo.service;

import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.ProductCategory;
import com.example.demo.domain.model.Stock;
import com.example.demo.domain.repository.ProductRepo;
import com.example.demo.domain.repository.StockRepo;
import com.example.demo.exceptions.ApplicationException;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.model.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    //    tworzenie
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



    public List<ProductDto> showProductById() {

        return productRepo.findAll().stream().map(product -> map(product)).collect(Collectors.toList()); //dla frontendu
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
    private Product map(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductId(productDto.getProductId());
        product.setProductType(productDto.getProductType());
        product.setPrice(productDto.getPrice());
//        product.setQuantity(productDto.getStock().getQuantity());
//        product.setProductCategory(categoryService.map(productDto.getProductCategory()));
        return product;
    }

//    update
public void updateProduct(ProductDto productDto,Long id){
//    if(!productRepo.existsById(id)){
//        throw new ApplicationException("No such product exists");
//    }
    Product productById = productRepo.findById(id).orElseThrow(()-> new ApplicationException("No such product exists"));
    productById.setProductName(productDto.getProductName());
    productById.setProductType(productDto.getProductType());
    productById.getProductCategory().setName(productDto.getProductCategory().getName());
    productById.getProductCategory().setType(productDto.getProductCategory().getType());
    productById.setPrice(productDto.getPrice());
    productRepo.save(productById);
    //    Product toBeUpdated= map(productDto);
//    toBeUpdated.setId(id);
//    return map(productRepo.save(toBeUpdated));
}
//    usuwanie
    public void deleteProduct(Long id){
        if (!productRepo.existsById(id)){
            throw new ApplicationException("No such product exists");
        }
        productRepo.deleteById(id);
    }

    }
