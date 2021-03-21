package com.example.demo.controller;

import com.example.demo.exceptions.ProductUseCategoryException;
import com.example.demo.model.ProductCategoryDto;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryHtmlController {

    private final CategoryService categoryService;

    public CategoryHtmlController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String showMainPage() {

        return "mainPage";
    }

    @GetMapping("/addCategory")
        public String addCategoryForm(Model model) {
model.addAttribute("productCategoryForm",new ProductCategoryDto());
        return "productCategoryForm";
    }

    @PostMapping("/addCategory")
    public String AddedCategory(@ModelAttribute("productCategoryForm") ProductCategoryDto productCategoryDto, Model model){
        ProductCategoryDto addedCategory = categoryService.createCategory(productCategoryDto);
        model.addAttribute("addedCategory",addedCategory);
        return "addedCategory";
    }


    @GetMapping("/categories")
    public String showAllCategories(Model model, @RequestParam (required = false, name="deleteError") Long id) {
        List<ProductCategoryDto> savedCategories = categoryService.showSavedCategories();
        model.addAttribute("allCategories",savedCategories);
        model.addAttribute("deleteErrorId",id);
        return "categories";
    }

    @PostMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable ("id") Long id){
        try {
            categoryService.deleteCategory(id);
        } catch (ProductUseCategoryException e) {
            return "redirect:/categories?deleteError="+id;
        }
        return "redirect:/categories";
    }


}
//th:each poleceniw  thymeleafie do tworzenia petli
//w liscie kategorii powinna byc jeszcze jedna kolumn az buttonem usun
//  @DeleteMapping("/{categoryid}")  ->post mapping   kozdy guzik ma miec inny adres (id ) usuwanej kategorii. guzik ma byc formularzem
