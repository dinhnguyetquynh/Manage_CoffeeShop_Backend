package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_dataservice.mapper.CustomerMapper;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    // GET all customers
    @GetMapping
//    public List<CustomerRes> getAllCustomers() {
//        List<Customer> lists = customerRepository.findAll();
//        List<CustomerRes> listRes = new ArrayList<>();
//        for(Customer cus:lists){
//            listRes.add(customerMapper.toCustomerRes(cus));
//        }
//        return listRes;
//    }
    public List<CustomerRes> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerRes)
                .collect(Collectors.toList());
    }


    // GET customer by ID
    @GetMapping("/{id}")
//    public CustomerRes getCustomerById(@PathVariable Integer id) {
//        Customer cus = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));
//        return customerMapper.toCustomerRes(cus);
//
//    }
    public CustomerRes getCustomerById(@PathVariable int id) {
        Customer cus = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toCustomerRes(cus);
    }

    // CREATE new customer
    @PostMapping
//    public ResponseEntity<CustomerRes> createCustomer(@Valid @RequestBody CustomerRequest request) {
//        Customer customer = new Customer();
//        customer.setCustomerName(request.getCustomerName());
//        customer.setCustomerPhone(request.getCustomerPhone());
//
//        Customer savedCustomer = customerRepository.save(customer);
//        CustomerRes customerRes = new CustomerRes();
//        customerRes.setCustomerId(savedCustomer.getCustomerId());
//        customerRes.setCustomerName(savedCustomer.getCustomerName());
//        customerRes.setCustomerPhone(savedCustomer.getCustomerPhone());
//
//        return ResponseEntity.ok(customerRes); // trả về JSON khách hàng vừa lưu
//    }
    public CustomerRes createCustomer(@Valid @RequestBody CustomerRequest req) {
        Customer entity = customerMapper.toCustomer(req);
        Customer saved = customerRepository.save(entity);
        return customerMapper.toCustomerRes(saved);
    }

    // UPDATE customer
    @PutMapping("/{id}")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerRequest request) {
//        return customerRepository.findById(id)
//                .map(existingCustomer -> {
//                    existingCustomer.setCustomerName(request.getCustomerName());
//                    existingCustomer.setCustomerPhone(request.getCustomerPhone());
//                    Customer updatedCustomer = customerRepository.save(existingCustomer);
//                    return ResponseEntity.ok(updatedCustomer);
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    public CustomerRes updateCustomer(@PathVariable int id, @Valid @RequestBody CustomerRequest req) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerMapper.updateCustomerFromReq(req, existing);
        Customer updated = customerRepository.save(existing);
        return customerMapper.toCustomerRes(updated);
    }

    // DELETE customer
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        try {
            customerRepository.deleteById(id);
            return "Delete Customer Successfully";
        }catch (RuntimeException ex){
            throw new RuntimeException("Delete customer failed");
        }
    }

    @GetMapping("/phone")
    public CustomerRes findCustomerByPhone(@RequestParam String phone){

        Customer cus = customerRepository.findByCustomerPhone(phone);
        if(cus==null){
            throw new RuntimeException("Customer not found");
        }
        return customerMapper.toCustomerRes(cus);

    }


}