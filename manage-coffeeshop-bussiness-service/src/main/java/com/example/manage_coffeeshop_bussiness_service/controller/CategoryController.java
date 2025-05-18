package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CategoryReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_bussiness_service.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/api/business/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public List<CategoryRes> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public CategoryRes getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryRes createCategory(@Valid @RequestBody CategoryReq categoryReq) {
        return categoryService.createCategory(categoryReq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable int id){
        return categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public CategoryRes updateCategory(@PathVariable int id,@Valid @RequestBody CategoryReq req){
        return categoryService.updateCategory(id,req);
    }
}
