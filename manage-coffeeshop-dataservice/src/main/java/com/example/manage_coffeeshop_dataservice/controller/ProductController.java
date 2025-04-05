package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.ProductRequest;
import com.example.manage_coffeeshop_dataservice.model.Category;
import com.example.manage_coffeeshop_dataservice.model.Product;
import com.example.manage_coffeeshop_dataservice.repository.CategoryRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new product
    @PostMapping
    public String createProduct(@RequestBody ProductRequest request) {
        try {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Product product = new Product();
            product.setProductName(request.getProductName());
            product.setProductPrice(request.getProductPrice());
            product.setProductInventoryQuantity(request.getProductInventoryQuantity());
            product.setProductImg(request.getProductImg());
            product.setCategory(category);
            productRepository.save(product);
            return "Tạo sản phẩm thành công";
        }catch (Exception e){
            e.printStackTrace();
            return "Tạo mới sản phẩm thất bại";

        }

    }

    // UPDATE product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Category category = categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));

                    existingProduct.setProductName(request.getProductName());
                    existingProduct.setProductPrice(request.getProductPrice());
                    existingProduct.setProductInventoryQuantity(request.getProductInventoryQuantity());
                    existingProduct.setProductImg(request.getProductImg());
                    existingProduct.setCategory(category);

                    Product updatedProduct = productRepository.save(existingProduct);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}