package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CartRequest {
    private String discountCode;
    private String paymentMethod;
//    private List<CartItemRequest> items;
}