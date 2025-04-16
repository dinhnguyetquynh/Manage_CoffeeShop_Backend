package com.example.manage_coffeeshop_bussiness_service.dto.request;

import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReq {
    @NotBlank(message = "Employee name không được để trống")
    private String empName;

    @NotNull(message = "Năm sinh không được để trống")
    @Min(value = 1900, message = "Năm sinh không hợp lệ")
    private int empYearOfBirth;

    @NotBlank(message = "Employee phone không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    private String empPhone;

    @NotNull(message = "Employee role không được null")
    private Role empRole;

    @NotBlank(message = "Employee account không được để trống")
    private String empAccount;

    @NotBlank(message = "Employee password không được để trống")
    private String empPassword;
}
