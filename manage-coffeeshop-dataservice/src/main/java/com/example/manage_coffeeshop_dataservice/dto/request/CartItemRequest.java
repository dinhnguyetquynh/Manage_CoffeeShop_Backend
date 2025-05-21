package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

@Data
public class  CartItemRequest {
    private Integer productId;
    private String size;
    private Integer quantity;
    private String sweet;
    private String ice;
    @Valid
    private List<CartToppingRequest> toppings;
}
