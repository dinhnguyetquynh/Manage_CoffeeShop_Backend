package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateReq {

    @NotBlank(message = "Số điện thoại không được để trống")
//    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 số")
    private String empPhone;

    @NotBlank(message = "Tài khoản không được để trống")
    private String empAccount;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String empPassword;
}
