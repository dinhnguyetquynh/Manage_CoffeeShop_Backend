package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ProductReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ProductRes;
import com.example.manage_coffeeshop_bussiness_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/business/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    public String addProduct(@RequestBody ProductReq req) {
        return productService.createProduct(req);
    }
}
