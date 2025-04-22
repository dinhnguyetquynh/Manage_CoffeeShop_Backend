package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    private int productId;
    private int productQuantity;
    private double subTotal;

    private String productName;
    private double productPrice;
}
