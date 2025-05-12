package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartToppingRes {
    private String toppingName;
    private Double toppingPrice;
    private Integer quantity;
}
