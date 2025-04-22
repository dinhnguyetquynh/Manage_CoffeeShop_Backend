package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.BillDetailReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.BillDetailRes;
import com.example.manage_coffeeshop_bussiness_service.service.BillDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/business/bill-details")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<BillDetailRes> getAllBillDetails() {
        return billDetailService.getAllBillDetails();
    }

    @GetMapping("/bill/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<BillDetailRes> getDetailsByBillId(@PathVariable int billId) {
        return billDetailService.getDetailsByBillId(billId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BillDetailRes createDetail(@Valid @RequestBody BillDetailReq req) {
        return billDetailService.createDetail(req);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BillDetailRes updateDetail(@Valid @RequestBody BillDetailReq req) {
        return billDetailService.updateDetail(req);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDetail(@RequestParam int billId, @RequestParam int productId) {
        return billDetailService.deleteDetail(billId, productId);
    }

}