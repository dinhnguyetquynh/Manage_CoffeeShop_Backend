package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class OrderOnlineRes {
    private int orderOnlID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime deliveryTime;
    private String deliveryAddress;
    private String noteOfCus;
    private double totalOrd;
    private String paymentMethod;
    private String transactionCode;
    private int customerId;
    private Boolean paid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private List<OrderOnlineDetailRes> ord_details;

}
