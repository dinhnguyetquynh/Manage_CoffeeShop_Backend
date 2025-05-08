package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeUpdateReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ApiRespone;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/api/business/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiRespone<EmployeeRes> createEmployee(@Valid @RequestBody EmployeeReq req) {
        ApiRespone<EmployeeRes> respone = new ApiRespone<>();
        respone.setResult(employeeService.createEmployee(req));
        return respone;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<EmployeeRes> getAllEmployees() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Account:{}",authentication.getName());
        log.info("Role:{}",authentication.getAuthorities());
        return employeeService.getAllEmployees();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{empId}")
    public EmployeeRes updateEmployee(@PathVariable int empId,@Valid @RequestBody EmployeeUpdateReq req) {
        return employeeService.updateEmployee(empId,req);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findEmployeeByAccount")
    public EmployeeRes findEmployeeByAccount(@RequestParam String account){
        EmployeeRes emp= employeeService.findEmployeeByAccount(account);
        if(emp==null){
            throw new RuntimeException("Employee not found");
        }
        return emp;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{empId}")
    public EmployeeRes findEmployeeById(@PathVariable int empId){
        return employeeService.findEmployeeById(empId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{empId}")
    public String deleteEmployeeById(@PathVariable int empId){
        return employeeService.deleteEmployeeById(empId);
    }
}
