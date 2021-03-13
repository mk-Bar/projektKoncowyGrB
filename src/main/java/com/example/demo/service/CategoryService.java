package com.example.demo.service;

import com.example.demo.domain.model.ProductCategory;
import com.example.demo.exceptions.ApplicationException;
import com.example.demo.domain.repository.CategoryRepo;
import com.example.demo.model.ProductCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CategoryService {

    private final CategoryRepo categoryRepo;//final zabezpiecza przed wartością categoryrepo= null

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

//tworzenie
    public ProductCategoryDto createCategory(ProductCategoryDto category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new ApplicationException("Category already exists");
        }
        return map(categoryRepo.save(map(category)));
    }
//wyswietlanie
    public List<ProductCategoryDto> showSavedCategories(){

        return categoryRepo.findAll().stream().map(productCategory -> map(productCategory)).collect(Collectors.toList()); //dla frontendu
    }

//update
    public ProductCategoryDto updateCategory(ProductCategoryDto category,Long id){
        if(!categoryRepo.existsById(id)){
          throw new ApplicationException("No such id exists");
        }
        ProductCategory toBeSaved= map(category);
        toBeSaved.setId(id);
        return map(categoryRepo.save(toBeSaved));
    }

//    usuwanie

    public void deleteCategory(Long id){
        if (!categoryRepo.existsById(id)){
            throw new ApplicationException("No such category exists");
        }
        categoryRepo.deleteById(id);
    }

//    dodanie do kategorii nowego pola


    public ProductCategoryDto map(ProductCategory productCategory){
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setName(productCategory.getName());
        productCategoryDto.setId(productCategory.getId());
        productCategoryDto.setType(productCategory.getType());
        return productCategoryDto;
    }

    private ProductCategory map(ProductCategoryDto productCategoryDto){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryDto.getName());
//        productCategory.setId(productCategoryDto.getId());
        productCategory.setType(productCategoryDto.getType());
        return productCategory;
    }


    public ProductCategory getById(Long id){
        return categoryRepo.findById(id).orElseThrow();
    }

}