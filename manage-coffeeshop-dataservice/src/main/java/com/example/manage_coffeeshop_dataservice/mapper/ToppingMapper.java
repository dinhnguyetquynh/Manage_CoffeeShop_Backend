package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.ToppingRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.ToppingRes;
import com.example.manage_coffeeshop_dataservice.model.Topping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToppingMapper {
    Topping toTopping(ToppingRequest req);
    ToppingRes toToppingRes(Topping topping);
}
