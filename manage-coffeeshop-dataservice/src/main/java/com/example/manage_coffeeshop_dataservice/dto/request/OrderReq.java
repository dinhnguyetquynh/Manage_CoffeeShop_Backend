package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq {
    private String customerId;
    private String employeeId;
    private LocalDate orderDate;
    private double orderTotal;
    private String paymentMethod;
    private List<OrderDetailReq> orderDetails;

}
