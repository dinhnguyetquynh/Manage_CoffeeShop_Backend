package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartToppingRequest {
    @NotBlank(message = "Tên topping không được để trống")
    private String toppingName;

    @NotNull(message = "Số lượng topping không được để trống")
    @Positive(message = "Số lượng topping phải là số dương")
    private Integer quantity;
}