package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerReq;
import com.example.manage_coffeeshop_bussiness_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/business/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public String createCustomer(@RequestBody CustomerReq customerReq) {
        return customerService.createCustomer(customerReq);
    }
}
