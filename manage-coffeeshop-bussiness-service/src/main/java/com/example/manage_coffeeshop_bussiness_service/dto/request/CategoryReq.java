package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryReq {
    @NotBlank(message = "Category name không được để trống")
    private String categoryName;

    @NotBlank(message = "Category description không được để trống")
    private String categoryDescription;

}
