package com.example.demo.service;

import com.example.demo.domain.model.ProductCategory;
import com.example.demo.exceptions.ApplicationException;
import com.example.demo.domain.repository.CategoryRepo;
import com.example.demo.model.ProductCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoryService {

    private final CategoryRepo categoryRepo;//final zabezpiecza przed wartością categoryrepo= null

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public ProductCategory createCategory(ProductCategoryDto category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new ApplicationException("Category already exists");
        }
        return categoryRepo.save(map(category));
    }

    public List<ProductCategoryDto> showSavedCategories(){

        return categoryRepo.findAll().stream().map(this::map).collect(Collectors.toList()); //dla frontendu
    }


    private ProductCategoryDto map(ProductCategory productCategory){
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setName(productCategory.getName());
        productCategoryDto.setId(productCategoryDto.getId());
        return productCategoryDto;
    }

    private ProductCategory map(ProductCategoryDto productCategoryDto){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryDto.getName());
        productCategory.setId(productCategoryDto.getId());
        return productCategory;
    }

}