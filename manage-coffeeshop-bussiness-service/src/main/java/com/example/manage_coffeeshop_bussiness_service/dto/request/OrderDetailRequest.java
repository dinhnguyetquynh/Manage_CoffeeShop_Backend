package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    @NotNull(message = "Mã sản phẩm không được để trống")
    @Min(value = 1, message = "Mã sản phẩm phải lớn hơn 0")
    private Integer productId;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Integer productQuantity;

    @NotNull(message = "Subtotal không được để trống")
    @Positive(message = "Subtotal phải là số dương")
    private Double subTotal;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm tối đa 100 ký tự")
    private String productName;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải là số dương")
    private Double productPrice;
}
