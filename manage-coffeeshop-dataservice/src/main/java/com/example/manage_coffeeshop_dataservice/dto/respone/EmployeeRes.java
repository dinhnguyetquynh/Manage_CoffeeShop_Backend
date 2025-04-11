package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.example.manage_coffeeshop_dataservice.enums.Role;
import lombok.Data;

@Data
public class EmployeeRes {
    private int empId;
    private String empName;
    private int empYearOfBirth;
    private String empPhone;
    private Role empRole;
    private String empAccount;
    private String empPassword;
}
