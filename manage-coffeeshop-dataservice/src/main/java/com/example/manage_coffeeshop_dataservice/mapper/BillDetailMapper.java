package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.BillDetailRequest;
import com.example.manage_coffeeshop_dataservice.model.BillDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillDetailMapper {
    BillDetail toBillDetail(BillDetailRequest req);

}