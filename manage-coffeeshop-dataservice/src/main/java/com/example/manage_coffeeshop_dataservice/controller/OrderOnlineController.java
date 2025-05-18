package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.OrderOnlineDetailReq;
import com.example.manage_coffeeshop_dataservice.dto.request.OrderOnlineRequest;
import com.example.manage_coffeeshop_dataservice.dto.request.ToppingReq;
import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.OrderRes;
import com.example.manage_coffeeshop_dataservice.mapper.OrderOnlineMapper;
import com.example.manage_coffeeshop_dataservice.mapper.OrderOnlineMapper2;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orderOnline")
public class OrderOnlineController {
    @Autowired
    private OrderTestRepository orderRepository;
    @Autowired
    private OrderOnlineRepository orderOnlineRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ToppingRepository toppingRepository;
//
//    @Autowired
//    private OrderOnlineMapper mapper;

    OrderOnlineMapper2 mapper = new OrderOnlineMapper2();


    @PostMapping("/create")
    public OrderOnlineRes createOrderOnline(@RequestBody OrderOnlineRequest request) {
        if (request.getTotalOrd() <= 0) {
            return null;
        }

        try {
            // Lấy customer từ DB
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            // Tạo order
            OrderOnline order = new OrderOnline();
            order.setDeliveryTime(request.getDeliveryTime());
            order.setDeliveryAddress(request.getDeliveryAddress());
            order.setNoteOfCus(request.getNoteOfCus());
            order.setTotalOrd(request.getTotalOrd());
            order.setPaymentMethod(request.getPaymentMethod());
            order.setTransactionCode(request.getTransactionCode());
            order.setPaid(false);
            order.setOrderDate(LocalDate.now());
            order.setCustomer(customer);

            List<OrderOnlineDetail> orderDetails = new ArrayList<>();

            for (OrderOnlineDetailReq detailReq : request.getOrderDetails()) {
                // Tìm sản phẩm
                Product product = productRepository.findById(detailReq.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                OrderOnlineDetail detail = new OrderOnlineDetail();
                detail.setProduct(product);
                detail.setSize(detailReq.getSize());
                detail.setUnitPrice(detailReq.getUnitPrice());
                detail.setQuantity(detailReq.getQuantity());
                detail.setSweet(detailReq.getSweet());
                detail.setIce(detailReq.getIce());
                detail.setOrderOnline(order);

                List<OrderOnlineDetailTopping> toppingList = new ArrayList<>();

                for (ToppingReq toppingReq : detailReq.getListTopping()) {
                    // Tìm topping theo tên
                    Topping topping = toppingRepository.findByToppingName(toppingReq.getToppingName())
                            .orElseThrow(() -> new RuntimeException("Topping not found: " + toppingReq.getToppingName()));

                    OrderOnlineDetailToppingKey key = new OrderOnlineDetailToppingKey();
                    OrderOnlineDetailTopping toppingLink = new OrderOnlineDetailTopping();
                    toppingLink.setId(key);
                    toppingLink.setOrderOnlineDetail(detail);
                    toppingLink.setTopping(topping);
                    toppingLink.setQuantity(toppingReq.getQuantity());

                    toppingList.add(toppingLink);
                }

                detail.setOrderOnlineDetailToppings(toppingList);
                orderDetails.add(detail);
            }

            order.setOrdDetails(orderDetails);

            // Lưu vào DB
            OrderOnline ordOnl = orderOnlineRepository.save(order);
            OrderOnlineRes resp = mapper.toOnlineRes(ordOnl);
            return resp;
        }catch (Exception e) {
            System.out.println("Error while create orderonline");
            return null;
        }
    }




}
