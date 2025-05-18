package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineRes;
import com.example.manage_coffeeshop_dataservice.model.OrderOnline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderOnlineMapper {
    OrderOnlineRes toOrderOnlineRes(OrderOnline ord);

}
