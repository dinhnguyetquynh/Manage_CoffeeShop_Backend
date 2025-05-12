package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CustomerRes;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(source = "birthDay", target = "birthday")
    Customer toCustomer(CustomerRequest req);

    @Mapping(source = "birthday", target = "birthDay")
    @Mapping(source = "rank_level", target = "rank")
    CustomerRes toCustomerRes(Customer cus);

}