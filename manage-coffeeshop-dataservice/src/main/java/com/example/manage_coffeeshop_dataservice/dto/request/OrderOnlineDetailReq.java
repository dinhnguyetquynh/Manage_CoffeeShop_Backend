package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderOnlineDetailReq {
    private int productId;
    private String size;
    private double unitPrice;
    private int quantity;
    private String sweet;
    private String ice;
    private List<ToppingReq> listTopping;
}
