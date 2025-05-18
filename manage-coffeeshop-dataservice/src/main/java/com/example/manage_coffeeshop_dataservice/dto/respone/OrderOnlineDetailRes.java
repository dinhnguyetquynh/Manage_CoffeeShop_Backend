package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.example.manage_coffeeshop_dataservice.model.Product;
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
