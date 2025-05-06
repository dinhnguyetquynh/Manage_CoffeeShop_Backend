package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_bussiness_service.service.AuthenticationServiceCus;
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
@Validated
@RequestMapping("/api/business/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AuthenticationServiceCus authenticationServiceCus;


    @PostMapping
    public ResponseEntity<CustomerRes> createCustomer(@RequestBody CustomerRequest customerReq) {
        CustomerRes createdCustomer = customerService.createCustomer(customerReq);
        return ResponseEntity.ok(createdCustomer); // Trả JSON object
    }

    @GetMapping("/phone")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CustomerRes> findCustomerByPhone(@RequestParam String phone){
        return ResponseEntity.ok(customerService.findCustomerByPhone(phone));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerRes> findCustomerById(@PathVariable int id){
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<CustomerRes>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping("/getInfo")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> getInfoCustomer(@RequestHeader("Authorization") String authHeader){
        CustomerRes infoCus = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("Token: " + token);

            String customerIdStr = authenticationServiceCus.extractCustomerIdFromToken(token);
            if(customerIdStr!=null && !customerIdStr.isEmpty()){
                int customerId = Integer.parseInt(customerIdStr);
               infoCus = customerService.findCustomerById(customerId);
                System.out.println("Info of Cus :"+infoCus);
            }else{
                System.out.println("Customer not found");
            }
        }
        return ResponseEntity.ok(infoCus);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerRes> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody CustomerRequest req) {
        CustomerRes updated = customerService.updateCustomer(id, req);
        return ResponseEntity.ok(updated);
    }
}
