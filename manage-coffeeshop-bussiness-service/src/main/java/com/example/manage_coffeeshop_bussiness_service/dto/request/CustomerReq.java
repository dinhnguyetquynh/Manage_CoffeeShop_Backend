package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerReq {
    @NotBlank(message = "Customer name không được để trống")
    private String customerName;

    @NotBlank(message = "Customer phone không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    private String customerPhone;
}
