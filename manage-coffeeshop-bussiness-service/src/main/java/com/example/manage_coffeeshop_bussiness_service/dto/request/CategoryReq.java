package com.example.manage_coffeeshop_bussiness_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryReq {

    private String categoryName;
    private String categoryDescription;

}
