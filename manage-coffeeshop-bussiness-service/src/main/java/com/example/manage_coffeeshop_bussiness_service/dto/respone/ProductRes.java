package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductRes {
    private  int productId;
    private String productName;
    private double productPrice;
    private int productInventoryQuantity;
    private String productImg;
    private String productDescription;
    @Min(value = 1, message = "Category ID phải lớn hơn 0")
    private int categoryId;
}
