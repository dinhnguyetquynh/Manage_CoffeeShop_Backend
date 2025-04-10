package com.example.manage_coffeeshop_dataservice.dto.respone;

import lombok.Data;

@Data
public class CustomerRes {
    private int customerId;
    private String customerName;
    private String customerPhone;
}
