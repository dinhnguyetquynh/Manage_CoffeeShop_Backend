package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.OtpRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationServiceCus;
import com.example.manage_coffeeshop_bussiness_service.service.CustomerService;
import com.example.manage_coffeeshop_bussiness_service.service.OtpService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/authCustomer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationCusCTRLLER {
    AuthenticationServiceCus authenticationServiceCus;

     OtpService otpService;
    CustomerService customerService;


    @PostMapping("/login")
    public AuthenticationRes authenticateForCus(@RequestBody AuthenticationRequest authenticationReq, HttpServletResponse response){
        return authenticationServiceCus.authenticate(authenticationReq,response);
    }


    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody CustomerRequest customer) {
        otpService.sendOtp(customer);
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());
        if (!isValid) {
            return ResponseEntity.badRequest().body("OTP KHONG HOP LE");
        }

        CustomerRequest customer = otpService.getCustomer(otpRequest.getEmail());
        log.info("Customer lay tu REdis la:"+customer);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found");
        }

        CustomerRes saved = customerService.createCustomer(customer);
        otpService.clearOtp(otpRequest.getEmail());

        return ResponseEntity.ok(saved);
    }


}
