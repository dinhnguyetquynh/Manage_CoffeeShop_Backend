package com.example.manage_coffeeshop_dataservice.dto.respone;

import lombok.Data;

@Data
public class ProductRes {
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private Integer categoryId;
}

