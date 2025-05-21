package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderOnlineRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.OrderOnlineRes;
import com.example.manage_coffeeshop_bussiness_service.service.OrderOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/api/business/sepay")
public class OrderSepayController {
    @Autowired
    private OrderOnlineService orderOnlineService;
    @Value("${sepay.bank}")
    private String sepayBank;

    @Value("${sepay.account}")
    private String sepayAccount;

    @PostMapping
    public Map<String, Object> creatOrderOnline(@RequestBody OrderOnlineRequest req){
        OrderOnlineRes ord= orderOnlineService.createOrderOnline(req);
        // Tạo mã QR SePay
        String qrUrl = String.format(
                "https://qr.sepay.vn/img?bank=%s&acc=%s&template=compact&amount=%.0f&des=DH%d",
                sepayBank, sepayAccount, ord.getTotalOrd(), ord.getOrderOnlID()
        );

        return (Map.of(
                "message", "Order created successfully",
                "orderId", ord.getOrderOnlID(),
                "total", ord.getTotalOrd(),
                "paid", ord.getPaid(),
                "qrUrl", qrUrl
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOnlineRes> checkStatusOrder(@PathVariable int id){
        OrderOnlineRes ordRes = orderOnlineService.checkStatusOrder(id);
        return ResponseEntity.ok(ordRes);
    }

    @GetMapping("/ordNYD")
    public List<OrderOnlineRes> getOrderNotYetDelivery(){
        return orderOnlineService.getOrdNotYetDelivery();
    }

    @PutMapping("/updateStatus/{id}")
    public Boolean updateStatusDelivery(@PathVariable int id){
        return orderOnlineService.updateOrderStatus(id);
    }


}
