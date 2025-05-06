package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"billDetails", "category", "cartItems"})

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


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public Product(int productId, String productName, double productPrice, int productInventoryQuantity, String productImg, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInventoryQuantity = productInventoryQuantity;
        this.productImg = productImg;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}