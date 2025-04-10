package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.example.manage_coffeeshop_dataservice.dto.request.OrderDetailReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes {
    private String customerId;
    private String employeeId;
    private LocalDate orderDate;
    private double orderTotal;
    private String paymentMethod;
    private List<OrderDetailRes> orderDetails;

}
