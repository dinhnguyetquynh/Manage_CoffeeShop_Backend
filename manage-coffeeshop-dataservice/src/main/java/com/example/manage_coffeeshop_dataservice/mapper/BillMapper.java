package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.BillRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.BillRes;
import com.example.manage_coffeeshop_dataservice.model.Bill;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Bill toBill(BillRequest req);

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "employee.empId", target = "employeeId")
    BillRes toBillRes(Bill bill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBillFromRequest(BillRequest request, @MappingTarget Bill entity);
}
