package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReq {
    private String empName;
    private int empYearOfBirth;
    private String empPhone;
    private int empRole;
    private String empAccount;
    private String empPassword;
}
