package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ToppingRequest {
    @NotBlank(message = "Tên Topping không được để trống")
    private String toppingName;

    @NotNull(message = "Giá Topping không được để trống")
    @Positive(message = "Giá Topping phải là số dương")
    private Double toppingPrice;

}
