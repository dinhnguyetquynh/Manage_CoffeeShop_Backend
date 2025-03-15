package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillRequest {
    private Integer customerId;
    private Integer employeeId;
    private LocalDateTime billCreationDate;
    private double billTotal;
    private String paymentMethod;
}