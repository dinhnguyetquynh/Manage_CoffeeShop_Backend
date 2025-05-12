package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OtpRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Phải đúng định dạng email")
    private String email;

    @NotBlank(message = "OTP không được để trống")
    @Size(min = 6, max = 6, message = "OTP phải có 6 chữ số")
    private String otp;
}
