package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRes {
    private int customerId;
    private String customerName;
    private String customerPhone;
    private String gender;
    private LocalDate birthDay;
    private String email;
    private String address;
    private String accountCus;
    private String passwordCus;
    private int accumulatedPoint;
    private String rank;
}
