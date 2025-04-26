package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(max = 50, message = "Tên khách hàng tối đa 50 ký tự")
    private String customerName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 số")
    private String customerPhone;

    @NotBlank
    private String gender;

    @NotNull
//    @Past
    private LocalDate dateOfBirth;

    @NotBlank
//    @Email
    private String email;

    @NotBlank
    private String address;
}