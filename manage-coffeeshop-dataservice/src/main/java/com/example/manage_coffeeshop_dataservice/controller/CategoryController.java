package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CategoryCreationReq;
import com.example.manage_coffeeshop_dataservice.dto.request.CategoryUpdateReq;
import com.example.manage_coffeeshop_dataservice.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_dataservice.mapper.CategoryMapper;
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

    @Autowired
    private CategoryMapper categoryMapper;

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
    public CategoryRes getCategoryById(@PathVariable int id) {
        Category categoryFound = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryRes(categoryFound);
    }

    @PutMapping("/{category_id}")
    public CategoryRes updateCategory(@PathVariable("category_id") int id, @RequestBody CategoryUpdateReq req) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
        category.setCategoryName(req.getCategoryName());
        category.setCategoryDescription(req.getCategoryDescription());
        return categoryMapper.toCategoryRes(categoryRepository.save(category));
    }
    @DeleteMapping("/{category_id}")
    public String deleteCategory(@PathVariable("category_id") int id) {
        try {
            categoryRepository.deleteById(id);
            return "Deleted Category successfully";
        } catch (RuntimeException ex) {
            throw new RuntimeException("Failed to delete Employee",ex);
        }

    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> getCategoryByName(@RequestParam String name) {
        boolean exists = categoryRepository.existsByCategoryName(name);
        return ResponseEntity.ok(exists);
    }



}
