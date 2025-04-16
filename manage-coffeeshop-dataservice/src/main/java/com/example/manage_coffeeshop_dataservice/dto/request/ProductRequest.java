package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private int categoryId;
    private String productDescription;
}
