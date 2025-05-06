package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartItemRes;
import com.example.manage_coffeeshop_dataservice.model.CartItem;
import com.example.manage_coffeeshop_dataservice.model.Topping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source="cartId", target="cart.cartId")
    @Mapping(source="productId", target="product.productId")
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "toppings", ignore = true)
    CartItem toCartItem(CartItemRequest req);

    @Mapping(source="cart.cartId", target="cartId")
    @Mapping(source="product.productId", target="productId")
    @Mapping(source="toppings", target="toppingIds")
    CartItemRes toCartItemRes(CartItem item);

    default List<Long> toppingsToToppingIds(List<Topping> toppings) {
        if (toppings == null) return null;
        return toppings.stream()
                .map(Topping::getToppingID)
                .collect(Collectors.toList());
    }
}