package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.Data;
import java.util.List;

@Data
public class CartRes {
    private Integer cartId;
    private Integer customerId;
    private Double total;
    private Integer quantity;
    private Double shipCost;
    private String discountCode;
    private String paymentMethod;
    private List<CartItemRes> cartItems;
}
