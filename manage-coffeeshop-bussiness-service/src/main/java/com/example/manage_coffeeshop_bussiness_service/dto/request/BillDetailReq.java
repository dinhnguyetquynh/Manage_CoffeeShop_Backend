package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailReq {

    @Min(value = 1, message = "billId phải lớn hơn 0")
    private int billId;

    @Min(value = 1, message = "productId phải lớn hơn 0")
    private int productId;

    @Min(value = 1, message = "Số lượng sản phẩm phải ít nhất là 1")
    private int productQuantity;

}