package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CartItemRequest {
    private Integer cartId;
    private Integer productId;
    private String size;
    private Double price;
    private Integer quantity;
    private Integer sweet;
    private Integer ice;
    private List<Long> toppingIds;
}
