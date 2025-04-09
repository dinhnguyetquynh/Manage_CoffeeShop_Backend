package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CategoryReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_bussiness_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
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

    @PostMapping
    public CategoryRes createCategory(@RequestBody CategoryReq categoryReq) {
        return categoryService.createCategory(categoryReq);
    }

    @DeleteMapping
    public String deleteCategory(@PathVariable int id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping
    public CategoryRes updateCategory(@PathVariable int id,@RequestBody CategoryReq req){
        return categoryService.updateCategory(id,req);
    }
}
