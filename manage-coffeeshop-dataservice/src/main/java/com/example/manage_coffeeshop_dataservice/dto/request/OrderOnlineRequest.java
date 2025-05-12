package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class OrderOnlineRequest {
    private LocalTime deliveryTime;
    private String deliveryAddress;
    private String noteOfCus;
    private double totalOrd;
    private String paymentMethod;
    private String transactionCode;
    private int customerId;
    private List<OrderOnlineDetailReq> orderDetails;

}
