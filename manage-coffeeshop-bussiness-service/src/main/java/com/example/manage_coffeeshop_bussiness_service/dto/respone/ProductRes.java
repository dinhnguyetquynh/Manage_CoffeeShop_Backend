package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.Data;

@Data
public class ProductRes {
        private int productId;
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private String productDescription;
    private int categoryId;
}
