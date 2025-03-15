package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class BillRequest {
    private Integer customerId;
    private Integer employeeId;
}