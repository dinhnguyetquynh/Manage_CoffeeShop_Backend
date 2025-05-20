package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.OrderOnlineDetailReq;
import com.example.manage_coffeeshop_dataservice.dto.request.OrderOnlineRequest;
import com.example.manage_coffeeshop_dataservice.dto.request.ToppingReq;
import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineDetailRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.ToppingRes;
import com.example.manage_coffeeshop_dataservice.enums.Rank;
import com.example.manage_coffeeshop_dataservice.mapper.OrderOnlineMapper2;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/sepay")
public class OrderOnlineSePay {
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

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;



    OrderOnlineMapper2 mapper = new OrderOnlineMapper2();

//    @PostMapping("/orders")
//    public ResponseEntity<?> createAndReturnOrder(@RequestBody OrderOnlineRequest request) {
//        if (request.getTotalOrd() <= 0) {
//            return ResponseEntity.badRequest().body("Invalid total amount");
//        }
//
//        try {
//            // Lấy customer từ DB
//            Customer customer = customerRepository.findById(request.getCustomerId())
//                    .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//            // Tạo order
//            OrderOnline order = new OrderOnline();
//            order.setDeliveryTime(request.getDeliveryTime());
//            order.setDeliveryAddress(request.getDeliveryAddress());
//            order.setNoteOfCus(request.getNoteOfCus());
//            order.setTotalOrd(request.getTotalOrd());
//            order.setPaymentMethod(request.getPaymentMethod());
//            order.setTransactionCode(request.getTransactionCode());
//            order.setPaid(false);
//            order.setOrderDate(LocalDate.now());
//            order.setCustomer(customer);
//
//            List<OrderOnlineDetail> orderDetails = new ArrayList<>();
//
//            for (OrderOnlineDetailReq detailReq : request.getOrderDetails()) {
//                // Tìm sản phẩm
//                Product product = productRepository.findById(detailReq.getProductId())
//                        .orElseThrow(() -> new RuntimeException("Product not found"));
//
//                OrderOnlineDetail detail = new OrderOnlineDetail();
//                detail.setProduct(product);
//                detail.setSize(detailReq.getSize());
//                detail.setUnitPrice(detailReq.getUnitPrice());
//                detail.setQuantity(detailReq.getQuantity());
//                detail.setSweet(detailReq.getSweet());
//                detail.setIce(detailReq.getIce());
//                detail.setOrderOnline(order);
//
//                List<OrderOnlineDetailTopping> toppingList = new ArrayList<>();
//
//                for (ToppingReq toppingReq : detailReq.getListTopping()) {
//                    // Tìm topping theo tên
//                    Topping topping = toppingRepository.findByToppingName(toppingReq.getToppingName())
//                            .orElseThrow(() -> new RuntimeException("Topping not found: " + toppingReq.getToppingName()));
//
//                    OrderOnlineDetailToppingKey key = new OrderOnlineDetailToppingKey();
//                    OrderOnlineDetailTopping toppingLink = new OrderOnlineDetailTopping();
//                    toppingLink.setId(key);
//                    toppingLink.setOrderOnlineDetail(detail);
//                    toppingLink.setTopping(topping);
//                    toppingLink.setQuantity(toppingReq.getQuantity());
//
//                    toppingList.add(toppingLink);
//                }
//
//                detail.setOrderOnlineDetailToppings(toppingList);
//                orderDetails.add(detail);
//            }
//
//            order.setOrdDetails(orderDetails);
//
//            // Lưu vào DB
//            orderOnlineRepository.save(order);
//
//            // Tạo mã QR SePay
//            String qrUrl = String.format(
//                    "https://qr.sepay.vn/img?bank=%s&acc=%s&template=compact&amount=%.0f&des=DH%d",
//                    sepayBank, sepayAccount, order.getTotalOrd(), order.getOrderOnlID()
//            );
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Order created successfully",
//                    "orderId", order.getOrderOnlID(),
//                    "total", order.getTotalOrd(),
//                    "paid", order.getPaid(),
//                    "qrUrl", qrUrl
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error creating order: " + e.getMessage());
//        }
//    }

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
            //set diem cho khach hang
            if(order.getTotalOrd()>=20000 && order.getTotalOrd()<30000){
                customer.setAccumulatedPoint(customer.getAccumulatedPoint()+2);
            } else if(order.getTotalOrd()>=30000 && order.getTotalOrd()<40000){
                customer.setAccumulatedPoint(customer.getAccumulatedPoint()+3);
            }else if(order.getTotalOrd()>=40000 && order.getTotalOrd()<50000){
                customer.setAccumulatedPoint(customer.getAccumulatedPoint()+4);
            }else if(order.getTotalOrd()>=50000 && order.getTotalOrd()<60000){
                customer.setAccumulatedPoint(customer.getAccumulatedPoint()+5);
            }else if(order.getTotalOrd()>=60000 && order.getTotalOrd()<70000) {
                customer.setAccumulatedPoint(customer.getAccumulatedPoint() + 6);
            }else if(order.getTotalOrd()>=70000 && order.getTotalOrd()<80000) {
                customer.setAccumulatedPoint(customer.getAccumulatedPoint() + 8);
            }else if(order.getTotalOrd()>=80000 && order.getTotalOrd()<90000) {
                customer.setAccumulatedPoint(customer.getAccumulatedPoint() + 8);
            }else if(order.getTotalOrd()>=90000&& order.getTotalOrd()<100000 ) {
                customer.setAccumulatedPoint(customer.getAccumulatedPoint() + 9);
            }else if(order.getTotalOrd()>=100000) {
                customer.setAccumulatedPoint(customer.getAccumulatedPoint() + 10);
            }
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


    // Webhook từ SePay (POST /api/sepay/webhook)
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook received: " + payload);
        try {
            String content = (String) payload.get("content");
            Object amountRaw = payload.get("transferAmount");

            if (content == null || amountRaw == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            // Tìm mã đơn hàng kiểu DH<number>
            Pattern pattern = Pattern.compile("DH(\\d+)");
            Matcher matcher = pattern.matcher(content);
            if (!matcher.find()) {
                return ResponseEntity.badRequest().body("No valid order ID found in content");
            }

            int orderId = Integer.parseInt(matcher.group(1));
            double amount = Double.parseDouble(amountRaw.toString());

            Optional<OrderOnline> optionalOrder = orderOnlineRepository.findById(orderId);
            if (optionalOrder.isEmpty()) {
                return ResponseEntity.badRequest().body("Order not found");
            }

            OrderOnline order = optionalOrder.get();
            if (order.getPaid()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Order already paid");
            }

            if (Math.round(order.getTotalOrd()) != Math.round(amount)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Amount mismatch");
            }

            order.setPaid(true);
            //set rank cho customer
            Customer cus = customerRepository.findById(order.getCustomer().getCustomerId()).orElseThrow(()->new RuntimeException("Customer not found"));
            if(cus.getAccumulatedPoint()<10){
                cus.setRank_level(Rank.MEMBER);
            }else if(cus.getAccumulatedPoint()>=20&&cus.getAccumulatedPoint()<30){
                cus.setRank_level(Rank.VIP);
            }else if(cus.getAccumulatedPoint()>=30){
                cus.setRank_level(Rank.DIAMOND);
            }
            customerRepository.save(cus);
            order.setCustomer(cus);

            OrderOnline newOrder=orderOnlineRepository.save(order);

            //Tao lich su giao dich
            TransactionHistory newHistory = new TransactionHistory();
            newHistory.setCustomer(newOrder.getCustomer());
            newHistory.setDate(newOrder.getOrderDate());
            newHistory.setTime(LocalTime.now());
            newHistory.setOrderOnline(newOrder);
            if(newOrder.getTotalOrd()>=20000 && newOrder.getTotalOrd()<30000){
                newHistory.setPlusPoint(2);
            } else if(newOrder.getTotalOrd()>=30000 && newOrder.getTotalOrd()<40000){
                newHistory.setPlusPoint(3);
            }else if(newOrder.getTotalOrd()>=40000 && newOrder.getTotalOrd()<50000){
                newHistory.setPlusPoint(4);
            }else if(newOrder.getTotalOrd()>=50000 && newOrder.getTotalOrd()<60000){
                newHistory.setPlusPoint(5);
            }else if(newOrder.getTotalOrd()>=60000 &&newOrder.getTotalOrd()<70000) {
                newHistory.setPlusPoint(6);
            }else if(newOrder.getTotalOrd()>=70000 && newOrder.getTotalOrd()<80000) {
                newHistory.setPlusPoint(7);
            }else if(newOrder.getTotalOrd()>=80000 && newOrder.getTotalOrd()<90000) {
                newHistory.setPlusPoint(8);
            }else if(newOrder.getTotalOrd()>=90000&& newOrder.getTotalOrd()<100000 ) {
                newHistory.setPlusPoint(9);
            }else if(newOrder.getTotalOrd()>=100000) {
                newHistory.setPlusPoint(10);
            }
            transactionHistoryRepository.save(newHistory);



            System.out.println("THANH TOAN THANH CONG ROI");
            return ResponseEntity.ok("Payment confirmed");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public OrderOnlineRes checkStatusOrder(@PathVariable int id) {
        OrderOnline ordFounded = orderOnlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!Boolean.TRUE.equals(ordFounded.getPaid())) {
            return null;
        }

        OrderOnlineRes orderRes = mapper.toOnlineRes(ordFounded);
        return orderRes;
    }
}
