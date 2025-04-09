package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CategoryCreationReq;
import com.example.manage_coffeeshop_dataservice.dto.request.CategoryUpdateReq;
import com.example.manage_coffeeshop_dataservice.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_dataservice.model.Category;
import com.example.manage_coffeeshop_dataservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryRes> getAllCategories() {
        List<Category> lists = categoryRepository.findAll();

        return lists.stream().map(category->{
            CategoryRes cate = new CategoryRes();
            cate.setCategoryId(category.getCategoryId());
            cate.setCategoryName(category.getCategoryName());
            cate.setCategoryDescription(category.getCategoryDescription());
            return cate;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public Category createCategory(@RequestBody CategoryCreationReq req) {
        Category category = new Category();
        category.setCategoryName(req.getCategoryName());
        category.setCategoryDescription(req.getCategoryDescription());

        return categoryRepository.save(category);

    }

    @GetMapping("/{category_id}")
    public Category getCategoryById(@PathVariable("category_id") int id) {
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    @PutMapping("/{category_id}")
    public Category updateCategory(@PathVariable("category_id") int id, @RequestBody CategoryUpdateReq req) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
        category.setCategoryName(req.getCategoryName());
        category.setCategoryDescription(req.getCategoryDescription());
        return categoryRepository.save(category);
    }
    @DeleteMapping("/{category_id}")
    public String deleteCategory(@PathVariable("category_id") int id) {
        categoryRepository.deleteById(id);
        return "Deleted Category";
    }


    @GetMapping("/exists")
    public ResponseEntity<Boolean> getCategoryByName(@RequestParam String name) {
        boolean exists = categoryRepository.existsByCategoryName(name);
        return ResponseEntity.ok(exists);
    }



}
