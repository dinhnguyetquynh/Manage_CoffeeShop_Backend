package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.ProductRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.ProductRes;
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

    // GET all products
    @GetMapping
    public List<ProductRes> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            ProductRes res = new ProductRes();
            res.setProductName(product.getProductName());
            res.setProductPrice(product.getProductPrice());
            res.setProductInventoryQuantity(product.getProductInventoryQuantity());
            res.setProductImg(product.getProductImg());
            res.setCategoryId(product.getCategory().getCategoryId()); // lấy id từ entity category
            return res;
        }).collect(Collectors.toList());
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
    public ResponseEntity<ProductRes> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
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
                    ProductRes res = new ProductRes();
                    res.setProductName(updatedProduct.getProductName());
                    res.setProductPrice(updatedProduct.getProductPrice());
                    res.setProductInventoryQuantity(updatedProduct.getProductInventoryQuantity());
                    res.setProductImg(updatedProduct.getProductImg());
                    res.setCategoryId(updatedProduct.getCategory().getCategoryId());
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
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
            ProductRes res = new ProductRes();
            res.setProductName(product.getProductName());
            res.setProductPrice(product.getProductPrice());
            res.setProductInventoryQuantity(product.getProductInventoryQuantity());
            res.setProductImg(product.getProductImg());
            res.setCategoryId(product.getCategory().getCategoryId()); // lấy id từ entity category
            return res;
        }).collect(Collectors.toList());
    }
}