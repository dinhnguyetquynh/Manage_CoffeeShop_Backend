package com.example.manage_coffeeshop_dataservice.dto.request;

import com.example.manage_coffeeshop_dataservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeReq {
    private String empName;
    private int empYearOfBirth;
    private String empPhone;
    private Role empRole;
    private String empAccount;
    private String empPassword;
}
