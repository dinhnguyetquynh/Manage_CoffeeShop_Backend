package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.Data;

@Data
public class ProductReq {
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private String productDescription;
    private int categoryId;
}
