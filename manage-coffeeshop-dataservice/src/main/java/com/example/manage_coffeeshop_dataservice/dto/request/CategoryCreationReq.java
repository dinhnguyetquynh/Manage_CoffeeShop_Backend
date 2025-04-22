package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreationReq {
    @NotBlank(message = "Category name không được để trống")
    private String categoryName;

    @NotBlank(message = "Category description không được để trống")
    private String categoryDescription;
}
