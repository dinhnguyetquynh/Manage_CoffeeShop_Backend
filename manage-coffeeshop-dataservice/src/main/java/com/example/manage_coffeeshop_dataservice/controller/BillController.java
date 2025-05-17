package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.BillRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.BillRes;
import com.example.manage_coffeeshop_dataservice.mapper.BillMapper;
import com.example.manage_coffeeshop_dataservice.model.Bill;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.model.Employee;
import com.example.manage_coffeeshop_dataservice.repository.BillRepository;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import com.example.manage_coffeeshop_dataservice.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BillMapper billMapper;

    @GetMapping
    public List<BillRes> getAllBills() {
        return billRepository.findAll().stream()
                .map(billMapper::toBillRes)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillRes> getBillById(@PathVariable Integer id) {
        return billRepository.findById(id)
                .map(bill -> ResponseEntity.ok(billMapper.toBillRes(bill)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BillRes> createBill(@RequestBody BillRequest request) {
        try {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId())); // Thêm thông báo lỗi chi tiết
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + request.getEmployeeId()));

            Bill bill = billMapper.toBill(request);
            bill.setCustomer(customer);
            bill.setEmployee(employee);

            Bill savedBill = billRepository.save(bill);
            return ResponseEntity.ok(billMapper.toBillRes(savedBill));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillRes> updateBill(@PathVariable Integer id, @Valid @RequestBody BillRequest request) {
        return billRepository.findById(id)
                .map(existingBill -> {
                    billMapper.updateBillFromRequest(request, existingBill);

                    if (existingBill.getCustomer().getCustomerId() != request.getCustomerId()) {
                        Customer customer = customerRepository.findById(request.getCustomerId())
                                .orElseThrow(() -> new RuntimeException("Customer not found"));
                        existingBill.setCustomer(customer);
                    }

                    if (existingBill.getEmployee().getEmpId() != request.getEmployeeId()) {
                        Employee employee = employeeRepository.findById(request.getEmployeeId())
                                .orElseThrow(() -> new RuntimeException("Employee not found"));
                        existingBill.setEmployee(employee);
                    }

                    Bill updatedBill = billRepository.save(existingBill);
                    return ResponseEntity.ok(billMapper.toBillRes(updatedBill));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Integer id) {
        return billRepository.findById(id)
                .map(bill -> {
                    billRepository.delete(bill);
                    return ResponseEntity.ok("Xóa thành công");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy bill có ID = " + id));
    }
}
