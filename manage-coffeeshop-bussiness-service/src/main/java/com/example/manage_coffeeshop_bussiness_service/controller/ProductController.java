package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ProductReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ProductRes;
import com.example.manage_coffeeshop_bussiness_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    public String addProduct(@RequestBody ProductReq req) {
        return productService.createProduct(req);
    }

    @GetMapping
    public List<ProductRes> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductRes findProductById(@PathVariable int id){
        return productService.findProductById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductRes> getProductsByCategory(@PathVariable int categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @PutMapping("/{id}")
    public ProductRes updateProduct(@PathVariable int id,@RequestBody ProductReq req){
        return productService.updateProduct(id,req);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){
        return productService.deleteProductById(id);
    }

}
