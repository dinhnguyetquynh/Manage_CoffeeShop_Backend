package com.example.manage_coffeeshop_bussiness_service.dto.request;

import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReq {

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String empName;

    @NotNull(message = "Năm sinh không được để trống")
    @Min(value = 1900, message = "Năm sinh phải sau năm 1900")
    @Max(value = 2025, message = "Năm sinh phải trước năm 2025")
    private int empYearOfBirth;

    @NotBlank(message = "Số điện thoại không được để trống")
//    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 số")
    private String empPhone;

    @NotNull(message = "Vai trò không được để trống")
    private Role empRole;

    @NotBlank(message = "Tài khoản không được để trống")
    private String empAccount;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String empPassword;
}
