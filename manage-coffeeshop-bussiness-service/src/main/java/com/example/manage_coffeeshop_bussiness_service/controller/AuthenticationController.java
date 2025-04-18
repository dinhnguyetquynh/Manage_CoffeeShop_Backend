package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.IntrospectReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.IntrospectRes;
import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationRes authenticate(@Valid @RequestBody AuthenticationRequest authenticationReq, HttpServletResponse response) {
        return authenticationService.authenticate(authenticationReq,response);

    }

    @PostMapping("/introspect")
    public IntrospectRes introspect(@Valid@RequestBody IntrospectReq introspectReq) throws ParseException, JOSEException {
        return authenticationService.introspect(introspectReq);

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationRes> refreshToken(HttpServletRequest request) throws ParseException, JOSEException {
        String refreshToken = authenticationService.extractRefreshTokenFromToken(request);
        // Gọi service để tạo access token mới từ refresh token
        AuthenticationRes authenticationRes = authenticationService.generateNewAccessToken(refreshToken);

        // Trả về kết quả dưới dạng ResponseEntity
        return ResponseEntity.ok(authenticationRes);
    }
}
