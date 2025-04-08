package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRes {
    private String token;
    private boolean authenticated;
    private String refreshToken;
}
