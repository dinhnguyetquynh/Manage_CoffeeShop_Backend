package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class OtpRequest {
    @Email
    private String email;
    private String otp;
}
