package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductReq {
    @NotBlank(message = "Product name không được để trống")
    private String productName;

    @PositiveOrZero(message = "Product price phải là số dương")
    private double productPrice;

    @Min(value = 0, message = "Product inventory quantity không được âm")
    private int productInventoryQuantity;

    @NotBlank(message = "Product image URL không được để trống")
    private String productImg;

    @NotBlank(message = "Product description không được để trống")
    private String productDescription;

    @Min(value = 1, message = "Category ID phải lớn hơn 0")
    private int categoryId;
}
