package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.OrderDetailReq;
import com.example.manage_coffeeshop_dataservice.dto.request.OrderReq;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public String createOrder(@RequestBody OrderReq req) {
        try {
            Bill bill = new Bill();
            Customer customer = customerRepository.findById(Integer.valueOf(req.getCustomerId())).orElseThrow(()->new RuntimeException("Not found customer"));
            bill.setCustomer(customer);
            Employee emp = employeeRepository.findById(Integer.valueOf(req.getCustomerId())).orElseThrow(()->new RuntimeException("Not found employee"));
            bill.setEmployee(emp);
            bill.setBillCreationDate(req.getOrderDate());
            bill.setBillTotal(req.getOrderTotal());
            bill.setPaymentMethod(req.getPaymentMethod());
            billRepository.save(bill);



            List<OrderDetailReq> orderDetailReqs = req.getOrderDetails();
            for(OrderDetailReq detail : orderDetailReqs ){
                BillDetail billDetail = new BillDetail();
                billDetail.setBill(bill);
                Product p = productRepository.findById(Integer.valueOf(detail.getProductId())).orElseThrow(()->new RuntimeException("Not found product"));

                billDetail.setProduct(p);
                billDetail.setProductQuantity(detail.getProductQuantity());
                billDetail.setSubTotal(detail.getSubTotal());


                billDetailRepository.save(billDetail);
            }
            return "Thanh toán thành công";
        }catch (Exception e){
            e.printStackTrace();
            return "Tạo order không thành công";
        }
    }
}
