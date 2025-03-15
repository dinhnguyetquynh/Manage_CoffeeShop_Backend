package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class BillDetailRequest {
    private int billId;
    private int productId;
    private int productQuantity;
    private double subTotal;
}