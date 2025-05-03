package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationServiceCus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/authCustomer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationCusCTRLLER {
    AuthenticationServiceCus authenticationServiceCus;

    @PostMapping("/login")
    public AuthenticationRes authenticateForCus(@RequestBody AuthenticationRequest authenticationReq, HttpServletResponse response){
        return authenticationServiceCus.authenticate(authenticationReq,response);
    }


}
