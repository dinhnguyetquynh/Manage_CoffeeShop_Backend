package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_dataservice.mapper.CustomerMapper;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    // GET all customers
    @GetMapping
    public List<CustomerRes> getAllCustomers() {
        List<Customer> lists = customerRepository.findAll();
        List<CustomerRes> listRes = new ArrayList<>();
        for(Customer cus:lists){
            listRes.add(mapper.toCustomerRes(cus));
        }
        return listRes;
    }

    // GET customer by ID
    @GetMapping("/{id}")
    public CustomerRes getCustomerById(@PathVariable Integer id) {
        Customer cus = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));
        return mapper.toCustomerRes(cus);

    }

    // CREATE new customer
    @PostMapping
    public ResponseEntity<CustomerRes> createCustomer(@RequestBody CustomerRequest request) {
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerPhone(request.getCustomerPhone());

        Customer savedCustomer = customerRepository.save(customer);
        CustomerRes customerRes = new CustomerRes();
        customerRes.setCustomerId(savedCustomer.getCustomerId());
        customerRes.setCustomerName(savedCustomer.getCustomerName());
        customerRes.setCustomerPhone(savedCustomer.getCustomerPhone());

        return ResponseEntity.ok(customerRes); // trả về JSON khách hàng vừa lưu
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
            System.out.println("Khong tim thay cus");
        }
        return mapper.toCustomerRes(cus);

    }


}