package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailReq {
    private int billId;
    private int productId;
    private int productQuantity;
//    private double subTotal;
}