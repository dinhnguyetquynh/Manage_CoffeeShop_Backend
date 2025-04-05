package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_dataservice.dto.request.EmployeeUpdateReq;
import com.example.manage_coffeeshop_dataservice.mapper.EmployeeMapper;
import com.example.manage_coffeeshop_dataservice.model.Employee;
import com.example.manage_coffeeshop_dataservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Employee findEmployeeByAccount(@RequestParam String account){
        return employeeRepository.findByEmpAccount(account);
    }

    @GetMapping("/list")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PutMapping("/{empId}")
    public Employee updateEmployee(@PathVariable int empId, @RequestBody EmployeeUpdateReq req){
        Employee emp = employeeRepository.findById(empId).get();
        emp.setEmpAccount(req.getEmpAccount());
        emp.setEmpPassword(req.getEmpPassword());
        emp.setEmpPhone(req.getEmpPhone());
        return employeeRepository.save(emp);
    }

}
