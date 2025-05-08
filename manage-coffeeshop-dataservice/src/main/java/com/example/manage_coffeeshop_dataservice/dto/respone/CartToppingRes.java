package com.example.manage_coffeeshop_dataservice.dto.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartToppingRes {
    private String toppingName;
    private Double toppingPrice;
    private Integer quantity;
}
