package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ProductReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ProductRes;
import com.example.manage_coffeeshop_bussiness_service.service.ProductService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/api/business/products")
public class ProductController {
    // Tạo bucket: cho phép 3 request mỗi 5 giây
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofSeconds(5))))
            .build();

    @Autowired private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createProduct(@Valid @RequestBody ProductReq req) {
        return productService.createProduct(req);
    }

    @GetMapping
    public List<ProductRes> getAllProducts(){
        // Rate limit kiểm tra
        if (!bucket.tryConsume(1)) {
            return (List<ProductRes>) ResponseEntity
                    .status(HttpStatus.SC_TOO_MANY_REQUESTS)
                    .body(Map.of("error", " Quá nhiều yêu cầu thanh toán. Vui lòng thử lại sau vài giây."));
        }
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductRes updateProduct(@PathVariable int id,@Valid @RequestBody ProductReq req){
        return productService.updateProduct(id,req);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){
        return productService.deleteProductById(id);
    }

}
