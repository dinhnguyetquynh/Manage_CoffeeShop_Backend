package com.example.manage_coffeeshop_dataservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreationReq {
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục tối đa 100 ký tự")
    private String categoryName;

    @NotBlank(message = "Mô tả danh mục không được để trống")
    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    private String categoryDescription;
}
