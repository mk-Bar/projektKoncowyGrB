package com.example.demo.controller;

import com.example.demo.exceptions.ProductUseCategoryException;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.model.ProductDto;
import com.example.demo.model.ProductFormDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductHtmlController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductHtmlController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/addProduct")
    public String addProductForm(Model model) {
        model.addAttribute("categoryList", categoryService.showSavedCategories());
        model.addAttribute("productDto", new ProductFormDto());
        return "productForm";
    }

    @PostMapping("/addProduct")
    public String AddedProduct(@ModelAttribute("productDto") ProductFormDto productFormDto) {
        productService.createProduct(productFormDto);
        return "redirect:/addProduct";
    }

    @GetMapping("/listOfProduct")
    public String showListOfProduct(Model model) {
        List<ProductDto> listOfProduct = productService.showSavedProducts();
        model.addAttribute("listOfProducts", listOfProduct);

        return "listOfProduct";
    }


    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);

        return "redirect:/listOfProduct";
    }
}
