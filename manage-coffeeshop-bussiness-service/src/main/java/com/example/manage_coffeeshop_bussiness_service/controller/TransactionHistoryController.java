package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.respone.TransactionHistoryRes;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationServiceCus;
import com.example.manage_coffeeshop_bussiness_service.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/api/business/history")
public class TransactionHistoryController {
    @Autowired
    private TransactionHistoryService historyService;

    @Autowired
    private AuthenticationServiceCus authenCusService;
    @GetMapping("/{customerId}")
    public ResponseEntity<List<TransactionHistoryRes>> getListHistory(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int customerId) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("Token: " + token);

            String customerIdStr = authenCusService.extractCustomerIdFromToken(token);
            int customerIdToken = Integer.parseInt(customerIdStr);

            if (customerId == customerIdToken) {
                List<TransactionHistoryRes> listRes = historyService.getAllTrHistory(customerId);
                return ResponseEntity.ok(listRes);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
