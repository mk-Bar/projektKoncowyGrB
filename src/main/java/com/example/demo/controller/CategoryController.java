package com.example.demo.controller;

import com.example.demo.domain.model.ProductCategory;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

        private final CategoryService categoryService;

        public CategoryController(final CategoryService categoryService) {
            this.categoryService = categoryService;
        }

        @PostMapping
//        @ResponseStatus(HttpStatus.CREATED)// do wyjasnienia
        public ProductCategory createCategory(@RequestBody ProductCategoryDto category) {
            return categoryService.createCategory(category);
        }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductCategoryDto> getAllCategories() {
        return categoryService.showSavedCategories();
    }

    }

