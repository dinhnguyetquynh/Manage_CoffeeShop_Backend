package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderRequest;
import com.example.manage_coffeeshop_bussiness_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public String createOrder(@RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }

}
