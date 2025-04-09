package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private Integer categoryId;// Tham chiếu đến Category
    private String productDescription;
}