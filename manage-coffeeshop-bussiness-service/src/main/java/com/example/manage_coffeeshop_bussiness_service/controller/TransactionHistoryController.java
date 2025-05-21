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
            @PathVariable int customerId) {
        List<TransactionHistoryRes> listRes = historyService.getAllTrHistory(customerId);
        return ResponseEntity.ok(listRes);
    }
}
