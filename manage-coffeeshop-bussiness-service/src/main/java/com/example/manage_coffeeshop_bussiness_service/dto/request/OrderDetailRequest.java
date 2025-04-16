package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    @Min(value = 1, message = "Product ID phải lớn hơn 0")
    private int productId;

    @Min(value = 1, message = "Số lượng sản phẩm phải ít nhất là 1")
    private int productQuantity;

    @PositiveOrZero(message = "Subtotal phải là số dương")
    private double subtotal;

    private String productName;
    private double productPrice;
}
