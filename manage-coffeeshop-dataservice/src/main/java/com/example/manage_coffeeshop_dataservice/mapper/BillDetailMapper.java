package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.BillDetailRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.BillDetailRes;
import com.example.manage_coffeeshop_dataservice.model.BillDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillDetailMapper {
    BillDetail toBillDetail(BillDetailRequest req);

    @Mapping(source = "id.billId", target = "billId")
    @Mapping(source = "id.productId", target = "productId")
    BillDetailRes toBillDetailRes(BillDetail billDetail);
}
