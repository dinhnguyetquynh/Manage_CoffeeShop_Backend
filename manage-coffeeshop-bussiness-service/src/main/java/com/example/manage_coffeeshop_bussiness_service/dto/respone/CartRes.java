package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.Data;

@Data
public class CartRes {
    private Integer cartId;
    private Integer customerId;
    private double total;
    private int quantity;
    private double shipCost;
    private String discountCode;
    private String paymentMethod;
}
