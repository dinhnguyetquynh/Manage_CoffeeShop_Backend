package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.example.manage_coffeeshop_dataservice.model.CartItem;
import com.example.manage_coffeeshop_dataservice.model.CartItemTopping;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartItemRes {
    private Long cartItemId;
    private String productName;
    private String size;
    private Double price;
    private Integer quantity;
    private String sweet;
    private String ice;
    private List<CartToppingRes> toppings;

    public static CartItemRes fromEntity(CartItem i) {
        CartItemRes r = new CartItemRes();
        r.cartItemId = i.getCartItemId();
        r.productName = i.getProduct().getProductName();
        r.size = i.getSize();
        r.price = i.getPrice();
        r.quantity = i.getQuantity();
        r.sweet = i.getSweet();
        r.ice = i.getIce();
        // --- sửa phần này ---
        r.toppings = i.getCartItemToppings().stream()
                .map(CartItemRes::toCartToppingRes)
                .collect(Collectors.toList());
        return r;
    }

    private static CartToppingRes toCartToppingRes(CartItemTopping cit) {
        return new CartToppingRes(
                cit.getTopping().getToppingName(),
                cit.getTopping().getToppingPrice(),
                cit.getQuantity()
        );
    }
}