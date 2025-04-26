package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailReq {
    @NotNull(message = "Mã sản phẩm không được để trống")
    @Min(value = 1, message = "Mã sản phẩm phải lớn hơn 0")
    private Integer productId;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Integer productQuantity;

    @NotNull(message = "Subtotal không được để trống")
    @Positive(message = "Subtotal phải là số dương")
    private Double subTotal;
}
