package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CartItemRequest {
//    @NotNull(message = "Mã giỏ hàng không được để trống")
//    @Min(value = 1, message = "Mã giỏ hàng phải lớn hơn 0")
//    private Long cartId;

    @NotNull(message = "Mã sản phẩm không được để trống")
    @Min(value = 1, message = "Mã sản phẩm phải lớn hơn 0")
    private Integer productId;

    @NotBlank(message = "Kích cỡ không được để trống")
    @Pattern(regexp = "S|M|L", message = "Kích cỡ phải là S, M hoặc L")
    private String size;

//    @NotNull(message = "Giá không được để trống")
//    @Positive(message = "Giá phải là số dương")
//    private Double price;

    @NotNull(message = "Số lượng không được để trống")
    @Positive(message = "Giá phải là số dương")
    private Integer quantity;

    @NotBlank(message = "Ngọt không được để trống")
    @Pattern(regexp = "Ít | Bình thường |Nhiều", message = "vị ngọt phải là Ít, Bình thường hoặc Nhiều")
    private String sweet;

    @NotBlank(message = "Đá không được để trống")
    @Pattern(regexp = "Ít|Bình Thường|Nhiều", message = "Đá phải là Ít, Bình thường hoặc Nhiều")
    private String ice;

    private List<CartToppingRequest> toppings;
}
