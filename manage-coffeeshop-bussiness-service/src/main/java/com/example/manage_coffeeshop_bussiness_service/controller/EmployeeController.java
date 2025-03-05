package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public EmployeeRes createEmployee(@RequestBody EmployeeReq req) {
        return employeeService.createEmployee(req);

    }

    @GetMapping
    public List<EmployeeRes> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
