package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BillDetailRequest {
    @Min(value = 1, message = "Bill ID phải lớn hơn 0")
    private int billId;

    @Min(value = 1, message = "Product ID phải lớn hơn 0")
    private int productId;

    @Min(value = 1, message = "Số lượng sản phẩm phải ít nhất là 1")
    private int productQuantity;
//    private double subTotal;
}