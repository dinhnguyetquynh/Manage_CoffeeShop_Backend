package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.ProductRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.ProductRes;
import com.example.manage_coffeeshop_dataservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.categoryId", target = "categoryId")
    ProductRes toProductRes(Product product);

    Product toProduct(ProductRequest req);
}