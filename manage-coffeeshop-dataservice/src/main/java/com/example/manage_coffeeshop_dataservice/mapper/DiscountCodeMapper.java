package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.DiscountCodeRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.DiscountCodeRes;
import com.example.manage_coffeeshop_dataservice.model.DiscountCode;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DiscountCodeMapper {
    @Mapping(source="discountCodeName", target="discountCodeName")
    DiscountCode toDiscountCode(DiscountCodeRequest req);
    DiscountCodeRes toDiscountCodeRes(DiscountCode discountCode);
    void updateFromReq(DiscountCodeRequest req, @MappingTarget DiscountCode discountCode);
}
