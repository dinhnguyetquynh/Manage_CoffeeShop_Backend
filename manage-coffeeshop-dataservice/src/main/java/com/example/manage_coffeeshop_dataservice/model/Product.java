package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private String productDescription;

    @OneToMany(mappedBy = "product")
    private List<BillDetail> billDetails = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    public Product(int productId, String productName, double productPrice, int productInventoryQuantity, String productImg, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInventoryQuantity = productInventoryQuantity;
        this.productImg = productImg;
        this.category = category;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }
}