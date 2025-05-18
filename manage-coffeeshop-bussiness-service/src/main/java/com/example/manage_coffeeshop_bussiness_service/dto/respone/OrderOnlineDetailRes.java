package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.Data;

import java.util.List;

@Data
public class OrderOnlineDetailRes {
    private int orderDetailID;
    private int productId;
    private String size;
    private double unitPrice;
    private int quantity;
    private String sweet;
    private String ice;
    List<ToppingRes> topping;
}
