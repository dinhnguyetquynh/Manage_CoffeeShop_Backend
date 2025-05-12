package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.OrderOnlineRequest;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.model.OrderOnline;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import com.example.manage_coffeeshop_dataservice.repository.OrderOnlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orderOnline")
public class OrderOnlineController {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private OrderOnlineRepository ordRepo;

    @PostMapping
    public ResponseEntity<?> createOrderOnline(@RequestBody OrderOnlineRequest req){
        OrderOnline ordOnl = new OrderOnline();
        ordOnl.setDeliveryAddress(req.getDeliveryAddress());
        Customer cus = customerRepository.findById(req.getCustomerId()).orElseThrow(()->new RuntimeException("Customer not found"));
        ordOnl.setCustomer(cus);
        ordOnl.setDeliveryTime(req.getDeliveryTime());
        ordOnl.setNoteOfCus(req.getNoteOfCus());
        ordOnl.setPaymentMethod(req.getPaymentMethod());
        ordOnl.setTotalOrd(req.getTotalOrd());
        ordOnl.setTransactionCode(req.getTransactionCode());

        OrderOnline newOrdOnl = ordRepo.save(ordOnl);

//        List<OrderOnline> listOrdOnl =

        return null;


    }

}
