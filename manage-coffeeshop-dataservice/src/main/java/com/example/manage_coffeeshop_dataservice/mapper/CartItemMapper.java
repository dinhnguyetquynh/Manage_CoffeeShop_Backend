package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.respone.CartItemRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartToppingRes;
import com.example.manage_coffeeshop_dataservice.model.CartItem;
import com.example.manage_coffeeshop_dataservice.model.CartItemTopping;
import com.example.manage_coffeeshop_dataservice.model.Topping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "cartItemId", target = "cartItemId")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "sweet", target = "sweet")
    @Mapping(source = "ice", target = "ice")
    @Mapping(target = "toppings", expression = "java(mapToppings(entity.getCartItemToppings()))")
    CartItemRes toDto(CartItem entity);

    default List<CartToppingRes> mapToppings(List<CartItemTopping> list) {
        return list.stream()
                .map(cit -> new CartToppingRes(
                        cit.getTopping().getToppingName(),
                        cit.getTopping().getToppingPrice(),
                        cit.getQuantity()))
                .collect(Collectors.toList());
    }
}
