package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class CartRequest {
    private Integer customerId;
    private double total;
    private int quantity;
    private double shipCost;
    private String discountCode;
    private String paymentMethod;
}