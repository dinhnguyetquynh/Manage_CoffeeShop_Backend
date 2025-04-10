package com.example.manage_coffeeshop_dataservice.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRes {
    private int productId;
    private int productQuantity;
    private double subTotal;
}
