package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
//
//    @NotNull(message = "Mã Khách hàng không được để trống")
//    @Min(value = 1, message = "Mã khách hàng phải lớn hơn 0")
//    private Integer customerId;
//
//    @NotNull(message = "Total không được để trống")
//    @Positive(message = "Total phải là số dương")
//    private Double total;
//
//    @NotNull(message = "Số lượng không được để trống")
//    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
//    private Integer quantity;
//
//    @NotNull(message = "ShipCost không được để trống")
//    @Positive(message = "ShipCost phải là số dương")
//    private Double shipCost;

    private String discountCode;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
//    @Pattern(regexp = "CASH|CARD|PAYPAL", message = "Phương thức thanh toán phải là CASH, CARD hoặc PAYPAL")
    private String paymentMethod;
//    private List<CartItemRequest> items;
}
