package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest req);
}