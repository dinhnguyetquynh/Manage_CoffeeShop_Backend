package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_dataservice.mapper.EmployeeMapper;
import com.example.manage_coffeeshop_dataservice.model.Employee;
import com.example.manage_coffeeshop_dataservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeReq req) {
        Employee emp = employeeMapper.toEmployee(req);
        return employeeRepository.save(emp);
    }

    @GetMapping
    public Employee getEmployeeByName(@RequestParam String name){
        return employeeRepository.findByEmpName(name);
    }
}
