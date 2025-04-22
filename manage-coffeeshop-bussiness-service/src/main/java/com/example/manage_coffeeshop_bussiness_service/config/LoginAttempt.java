package com.example.manage_coffeeshop_bussiness_service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttempt {
    private int attempts;
    private long lastAttempt;
}