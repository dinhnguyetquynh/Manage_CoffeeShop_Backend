package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillReq {
    private int customerId;
    private int employeeId;
    private LocalDateTime billCreationDate;
    private double billTotal;
    private String paymentMethod;
}