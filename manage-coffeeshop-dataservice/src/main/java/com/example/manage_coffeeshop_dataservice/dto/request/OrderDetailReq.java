package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailReq {
    private int productId;
    private int productQuantity;
    private double subTotal;
}
