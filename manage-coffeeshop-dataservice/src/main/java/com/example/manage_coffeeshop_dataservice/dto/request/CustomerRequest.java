package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CustomerRequest {
    private String customerName;
    private String customerPhone;
    private String gender;
    private LocalDate birthDay;
    private String email;
    private String address;
    private String accountCus;
    private String passwordCus;


}