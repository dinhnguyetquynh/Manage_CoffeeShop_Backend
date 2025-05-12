package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CartToppingRequest {
    @NotBlank
    private String toppingName;
    @NotNull
    @Positive
    private Integer quantity;
}