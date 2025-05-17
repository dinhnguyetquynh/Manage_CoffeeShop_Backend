package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.CartRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartRes;
import com.example.manage_coffeeshop_dataservice.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartRequest req);
    @Mapping(source="customer.customerId", target="customerId")
    CartRes toCartRes(Cart cart);
}

