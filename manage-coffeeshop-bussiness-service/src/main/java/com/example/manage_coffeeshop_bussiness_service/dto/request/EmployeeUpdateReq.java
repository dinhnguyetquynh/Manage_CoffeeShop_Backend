package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateReq {
    @NotBlank(message = "Employee account không được để trống")
    private String empAccount;

    @NotBlank(message = "Employee password không được để trống")
    private String empPassword;

    @NotBlank(message = "Employee phone không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    private String empPhone;
}
