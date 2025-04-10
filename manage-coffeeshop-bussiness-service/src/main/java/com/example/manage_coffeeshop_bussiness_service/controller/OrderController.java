package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.OrderRes;
import com.example.manage_coffeeshop_bussiness_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createOrder(@RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderRes> findOrderById(@PathVariable int id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/date")
    public ResponseEntity<OrderRes> findOrderByDate(@RequestParam String date){
        return ResponseEntity.ok(orderService.findOrderByDate(date));
    }

}
