package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRes {
    private int billId;
    private LocalDate billCreationDate;
    private double billTotal;
    private String paymentMethod;
    private int customerId;
    private int employeeId;
}