package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.request.CategoryCreationReq;
import com.example.manage_coffeeshop_dataservice.dto.request.CategoryUpdateReq;
import com.example.manage_coffeeshop_dataservice.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_dataservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationReq req);
    CategoryRes toCategoryRes(Category category);
}