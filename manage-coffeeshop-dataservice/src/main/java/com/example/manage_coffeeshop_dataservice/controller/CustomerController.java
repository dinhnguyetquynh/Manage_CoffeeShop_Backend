package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    // GET all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // GET customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new customer
    @PostMapping
    public String createCustomer(@RequestBody CustomerRequest request) {
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerPhone(request.getCustomerPhone());
        customerRepository.save(customer);
        return "Thêm khách hàng mới thành công";
    }

    // UPDATE customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody CustomerRequest request) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setCustomerName(request.getCustomerName());
                    existingCustomer.setCustomerPhone(request.getCustomerPhone());
                    Customer updatedCustomer = customerRepository.save(existingCustomer);
                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE customer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}