package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.ProductRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.ProductRes;
import com.example.manage_coffeeshop_dataservice.mapper.ProductMapper;
import com.example.manage_coffeeshop_dataservice.model.Category;
import com.example.manage_coffeeshop_dataservice.model.Product;
import com.example.manage_coffeeshop_dataservice.repository.CategoryRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // GET all products
    @GetMapping
    public List<ProductRes> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            ProductRes res = new ProductRes();
            res.setProductId(product.getProductId());
            res.setProductName(product.getProductName());
            res.setProductPrice(product.getProductPrice());
            res.setProductInventoryQuantity(product.getProductInventoryQuantity());
            res.setProductImg(product.getProductImg());
            res.setProductDescription(product.getProductDescription());
            res.setCategoryId(product.getCategory().getCategoryId());
            return res;
        }).collect(Collectors.toList());
    }

    // GET product by ID
    @GetMapping("/{id}")
    public ProductRes getProductById(@PathVariable Integer id) {
        Product pd = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductRes(pd);
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
            product.setProductDescription(request.getProductDescription());
            productRepository.save(product);
            return "Tạo sản phẩm thành công";
        }catch (Exception e){
            e.printStackTrace();
            return "Tạo mới sản phẩm thất bại";

        }

    }

    // UPDATE product
    @PutMapping("/{id}")
    public ProductRes updateProduct(@PathVariable int id, @RequestBody ProductRequest request) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getCategoryId() != updateProduct.getCategory().getCategoryId()) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            updateProduct.setCategory(newCategory);
        }

        updateProduct.setProductName(request.getProductName());
        updateProduct.setProductPrice(request.getProductPrice());
        updateProduct.setProductInventoryQuantity(request.getProductInventoryQuantity());
        updateProduct.setProductImg(request.getProductImg());
        updateProduct.setProductDescription(request.getProductDescription());

        productRepository.save(updateProduct);
        return productMapper.toProductRes(updateProduct);
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok("Xóa sản phẩm thành công");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy sản phẩm có ID = " + id));
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductRes> getProductsByCategoryId(@PathVariable int categoryId){
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);

        return products.stream().map(product -> {
            ProductRes res = productMapper.toProductRes(product);
            return res;
        }).collect(Collectors.toList());
    }
}