package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreationReq {
    private String categoryName;
    private String categoryDescription;
}
