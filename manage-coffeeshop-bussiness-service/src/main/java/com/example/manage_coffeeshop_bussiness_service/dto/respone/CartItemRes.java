package com.example.manage_coffeeshop_bussiness_service.dto.respone;
import lombok.Data;
import java.util.List;

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
}
