package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductReq {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm tối đa 100 ký tự")
    private String productName;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải là số dương")
    private Double productPrice;

    @NotNull(message = "số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn không âm")
    private Integer productInventoryQuantity;

    @NotBlank(message = "Hình ảnh sản phẩm không được để trống")
    private String productImg;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String productDescription;

    @NotNull(message = "Mã Danh mục không được để trống")
    @Min(value = 1, message = "Mã Danh mục phải lớn hơn 0")
    private Integer categoryId;
}
