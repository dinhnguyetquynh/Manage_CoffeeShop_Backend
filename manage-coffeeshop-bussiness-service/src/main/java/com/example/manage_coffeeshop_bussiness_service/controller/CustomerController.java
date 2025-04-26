package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_bussiness_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/business/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<CustomerRes> createCustomer(@Valid @RequestBody CustomerReq customerReq) {
        CustomerRes createdCustomer = customerService.createCustomer(customerReq);
        return ResponseEntity.ok(createdCustomer); // Trả JSON object
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CustomerRes> updateCustomer(@PathVariable int id,
                                                      @Valid @RequestBody CustomerReq req) {
        return ResponseEntity.ok(customerService.updateCustomer(id, req));
    }

    @GetMapping("/phone")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CustomerRes> getCustomerByPhone(@RequestParam String phone){
        return ResponseEntity.ok(customerService.findCustomerByPhone(phone));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CustomerRes> getCustomerById(@PathVariable int id){
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerRes>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

}
