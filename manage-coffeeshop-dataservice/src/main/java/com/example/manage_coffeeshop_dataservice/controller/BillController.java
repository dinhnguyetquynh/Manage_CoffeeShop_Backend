package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.BillRequest;
import com.example.manage_coffeeshop_dataservice.model.Bill;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.model.Employee;
import com.example.manage_coffeeshop_dataservice.repository.BillRepository;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import com.example.manage_coffeeshop_dataservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET all bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // GET bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Integer id) {
        Optional<Bill> bill = billRepository.findById(id);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new bill
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setEmployee(employee);

        Bill savedBill = billRepository.save(bill);
        return ResponseEntity.ok(savedBill);
    }

    // DELETE bill
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Integer id) {
        return billRepository.findById(id)
                .map(bill -> {
                    billRepository.delete(bill);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}