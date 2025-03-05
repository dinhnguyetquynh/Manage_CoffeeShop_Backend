package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.IntrospectReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.IntrospectRes;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/business/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationRes authenticate(@RequestBody AuthenticationRequest authenticationReq) {
        return authenticationService.authenticate(authenticationReq);

    }

    @PostMapping("/introspect")
    public IntrospectRes introspect(@RequestBody IntrospectReq introspectReq) throws ParseException, JOSEException {
        return authenticationService.introspect(introspectReq);

    }

}
