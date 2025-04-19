package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRes {
    private String token;
    private boolean authenticated;
    private String refreshToken;
    private String message;  // Thêm thuộc tính message để chứa thông điệp lỗi

    // Constructor để tạo đối tượng khi có lỗi
    public AuthenticationRes(String message) {
        this.message = message;
    }
}
