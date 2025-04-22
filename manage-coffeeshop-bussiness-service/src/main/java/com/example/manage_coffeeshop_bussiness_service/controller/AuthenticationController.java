package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.config.LoginAttempt;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

// Dùng ConcurrentHashMap để lưu trữ số lần đăng nhập và thời gian
private static final ConcurrentHashMap<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;  // Tối đa 5 lần đăng nhập
    private static final long LOCK_TIME_DURATION = TimeUnit.MINUTES.toMillis(1);  // Giới hạn thời gian khóa 30 phút

    @PostMapping("/login")
    public AuthenticationRes  authenticate(@RequestBody AuthenticationRequest authenticationReq, HttpServletResponse response) {
        String username = authenticationReq.getUsername();

        // Kiểm tra số lần đăng nhập và thời gian
        if (isRateLimitExceeded(username)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // HTTP 429
            return new AuthenticationRes("You have exceeded the maximum number of login attempts. Please try again later.");    }

        // Nếu chưa vượt quá giới hạn, gọi service xác thực
        return authenticationService.authenticate(authenticationReq, response);
    }

    private boolean isRateLimitExceeded(String username) {
        LoginAttempt attempt = loginAttempts.get(username);

        // Nếu không có bản ghi đăng nhập hoặc thời gian quá lâu, reset số lần đăng nhập
        if (attempt == null || System.currentTimeMillis() - attempt.getLastAttempt() > LOCK_TIME_DURATION) {
            loginAttempts.put(username, new LoginAttempt(1, System.currentTimeMillis()));
            return false;
        }

        // Kiểm tra xem số lần đăng nhập có vượt quá giới hạn không
        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            return true;
        }

        // Cập nhật số lần đăng nhập
        loginAttempts.put(username, new LoginAttempt(attempt.getAttempts() + 1, System.currentTimeMillis()));
        return false;
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
