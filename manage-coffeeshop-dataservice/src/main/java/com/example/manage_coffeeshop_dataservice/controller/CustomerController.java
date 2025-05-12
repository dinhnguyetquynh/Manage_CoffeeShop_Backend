package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_dataservice.mapper.CustomerMapper;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.enums.Gender;
import com.example.manage_coffeeshop_dataservice.enums.Rank;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    // GET all customers
    @GetMapping
    public List<CustomerRes> getAllCustomers() {
        List<Customer> lists = customerRepository.findAll();
        List<CustomerRes> listRes = new ArrayList<>();
        for(Customer cus:lists){
            CustomerRes res = new CustomerRes();
            res.setCustomerId(cus.getCustomerId());
            res.setCustomerName(cus.getCustomerName());
            res.setCustomerPhone(cus.getCustomerPhone());
            res.setGender(cus.getGender().getDisplayName());
            res.setBirthDay(cus.getBirthday());
            res.setEmail(cus.getEmail());
            res.setAddress(cus.getAddress());
            res.setAccountCus(cus.getAccountCus());
            res.setPasswordCus(cus.getPasswordCus());
            res.setAccumulatedPoint(cus.getAccumulatedPoint());
            res.setRank(cus.getRank_level().name());
            listRes.add(res);
        }
        return listRes;
    }

    // GET customer by ID
    @GetMapping("/{id}")
    public CustomerRes getCustomerById(@PathVariable Integer id) {
        Customer cus = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));

        CustomerRes res = new CustomerRes();
        res.setCustomerId(cus.getCustomerId());
        res.setCustomerName(cus.getCustomerName());
        res.setCustomerPhone(cus.getCustomerPhone());
        res.setGender(cus.getGender().getDisplayName());
        res.setBirthDay(cus.getBirthday());
        res.setEmail(cus.getEmail());
        res.setAddress(cus.getAddress());
        res.setAccountCus(cus.getAccountCus());
        res.setPasswordCus(cus.getPasswordCus());
        res.setAccumulatedPoint(cus.getAccumulatedPoint());
        res.setRank(cus.getRank_level().name());
        return res;

    }

    // CREATE new customer
    @PostMapping
    public CustomerRes createCustomer(@RequestBody CustomerRequest req) {
        System.out.println("Customer nhan duoc la:"+req.toString());

        Customer customer = new Customer();

        customer.setCustomerName( req.getCustomerName() );
        customer.setCustomerPhone( req.getCustomerPhone() );
        if ( req.getGender() != null ) {
            customer.setGender(Gender.fromDisplayName(req.getGender()));
        }
        customer.setBirthday(req.getBirthDay());
        customer.setEmail( req.getEmail() );
        customer.setAddress( req.getAddress() );
        customer.setAccountCus( req.getAccountCus() );
        customer.setPasswordCus( req.getPasswordCus() );
        customer.setAccumulatedPoint(0);
        customer.setRank_level(Rank.MEMBER);
        Customer createdCus = customerRepository.save(customer);
        //Map customer thanfh customer res: customerId,Name,Phone;gender;
        // birthDay;email;address;accountCus;passwordCus;accumulatedPoint;rank;

        CustomerRes res = new CustomerRes();
        res.setCustomerId(createdCus.getCustomerId());
        res.setCustomerName(createdCus.getCustomerName());
        res.setCustomerPhone(createdCus.getCustomerPhone());

        if(createdCus.getGender()!=null){
            res.setGender(createdCus.getGender().getDisplayName());
        }

        res.setBirthDay(createdCus.getBirthday());
        res.setEmail(createdCus.getEmail());
        res.setAddress(createdCus.getAddress());
        res.setAccountCus(createdCus.getAccountCus());
        res.setPasswordCus(createdCus.getPasswordCus());
        res.setAccumulatedPoint(createdCus.getAccumulatedPoint());
        res.setRank(createdCus.getRank_level().name());
        return res;

    }

    // UPDATE customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerRes> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequest request) {

        return customerRepository.findById(id)
                .map(existing -> {
                    // Cập nhật tất cả các trường
                    existing.setCustomerName(request.getCustomerName());
                    existing.setCustomerPhone(request.getCustomerPhone());
                    existing.setGender(Gender.fromDisplayName(request.getGender()));
                    existing.setBirthday(request.getBirthDay());
                    existing.setEmail(request.getEmail());
                    existing.setAddress(request.getAddress());
                    existing.setAccountCus(request.getAccountCus());
                    existing.setPasswordCus(request.getPasswordCus());

                    Customer saved = customerRepository.save(existing);
                    CustomerRes res = customerMapper.toCustomerRes(saved);
                    return ResponseEntity.ok(res);
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
        CustomerRes res = new CustomerRes();
        if(cus==null){
            System.out.println("Khong tim thay cus");
        }else{
            res.setCustomerId(cus.getCustomerId());
            res.setCustomerName(cus.getCustomerName());
            res.setCustomerPhone(cus.getCustomerPhone());
            res.setGender(cus.getGender().getDisplayName());
            res.setBirthDay(cus.getBirthday());
            res.setEmail(cus.getEmail());
            res.setAddress(cus.getAddress());
            res.setAccountCus(cus.getAccountCus());
            res.setPasswordCus(cus.getPasswordCus());
            res.setAccumulatedPoint(cus.getAccumulatedPoint());
            res.setRank(cus.getRank_level().name());
        }
        return res;

    }

    @GetMapping("/account")
    public CustomerRes findCustomerByAccount(@RequestParam String account){
        Customer cus = customerRepository.findByAccountCus(account);
        CustomerRes res = new CustomerRes();
        if(cus==null){
            System.out.println("Khong tim thay cus");
        }else{
            res.setCustomerId(cus.getCustomerId());
            res.setCustomerName(cus.getCustomerName());
            res.setCustomerPhone(cus.getCustomerPhone());
            res.setGender(cus.getGender().getDisplayName());
            res.setBirthDay(cus.getBirthday());
            res.setEmail(cus.getEmail());
            res.setAddress(cus.getAddress());
            res.setAccountCus(cus.getAccountCus());
            res.setPasswordCus(cus.getPasswordCus());
            res.setAccumulatedPoint(cus.getAccumulatedPoint());
            res.setRank(cus.getRank_level().name());

        }
        return res;
    }


}