package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateReq {


    private String empPhone;

    private String empAccount;
    private String empPassword;
}
