package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailReq {

    @NotNull(message = "Bill ID không được để trống")
    @Min(value = 1, message = "Bill ID phải lớn hơn 0")
    private Integer billId;

    @NotNull(message = "Product ID không được để trống")
    @Min(value = 1, message = "Product ID phải lớn hơn 0")
    private Integer productId;

    @NotNull(message = "ProductQuantity không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm phải ít nhất là 1")
    private Integer productQuantity;

}