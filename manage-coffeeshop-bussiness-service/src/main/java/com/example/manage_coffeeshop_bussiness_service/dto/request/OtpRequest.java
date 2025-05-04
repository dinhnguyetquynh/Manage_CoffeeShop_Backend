package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otp;
}
