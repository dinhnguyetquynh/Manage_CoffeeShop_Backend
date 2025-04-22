package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes {
    private int billId;
    private String customerId;
    private String employeeId;
    private LocalDate orderDate;
    private double orderTotal;
    private String paymentMethod;
    private List<OrderDetailRequest> orderDetails;
}
