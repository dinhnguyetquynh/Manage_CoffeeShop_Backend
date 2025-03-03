package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "product")
    private Set<BillDetail> billDetails = new HashSet<>();

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
}
