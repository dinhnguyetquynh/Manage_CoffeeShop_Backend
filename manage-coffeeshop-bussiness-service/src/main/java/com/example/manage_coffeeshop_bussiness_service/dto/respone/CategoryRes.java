package com.example.manage_coffeeshop_bussiness_service.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRes {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
}
