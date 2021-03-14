package com.example.demo.controller;

import com.example.demo.domain.model.ProductCategory;
import com.example.demo.exceptions.ProductUseCategoryException;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

        private final CategoryService categoryService;

        public CategoryController(final CategoryService categoryService) {
            this.categoryService = categoryService;
        }


//        dodawanie kategorii
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public ProductCategoryDto createCategory(@RequestBody ProductCategoryDto category) {
            return categoryService.createCategory(category);
        }

//        update kategorii
@PutMapping("/{categoryid}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void updateUser(@PathVariable Long categoryid,  @RequestBody ProductCategoryDto category) {
    categoryService.updateCategory(category, categoryid);
}

// wyswietlanie wszystkich kategorii
    @GetMapping  //(produces = MediaType.APPLICATION_JSON_VALUE) Json jest domyslnm formatem- to nie jest konieczne
    public List<ProductCategoryDto> getAllCategories() {
        return categoryService.showSavedCategories();
    }

//usuwanie kategorii po podanym w parametrze metody id
    @DeleteMapping("/{categoryid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long categoryid) throws ProductUseCategoryException {
        categoryService.deleteCategory(categoryid);
    }

    }

