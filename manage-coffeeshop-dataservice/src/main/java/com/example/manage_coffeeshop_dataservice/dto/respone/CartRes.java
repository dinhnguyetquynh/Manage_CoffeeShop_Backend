package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.example.manage_coffeeshop_dataservice.model.Cart;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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

    public static CartRes fromEntity(Cart c) {
        CartRes r = new CartRes();
        r.cartId = c.getCartId();
        r.customerId = c.getCustomer().getCustomerId();
        r.total = c.getTotal();
        r.quantity = c.getQuantity();
        r.shipCost = c.getShipCost();
        r.discountCode = c.getDiscountCode();
        r.paymentMethod = c.getPaymentMethod();
        // mapping qua CartItemRes đã sửa
        r.cartItems = c.getCartItems().stream()
                .map(CartItemRes::fromEntity)
                .collect(Collectors.toList());
        return r;
    }
}
