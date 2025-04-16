package com.example.manage_coffeeshop_bussiness_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "billID không được để null")
    private int billId;

    @NotBlank(message = "Customer ID không được để trống")
    private String customerId;

    @NotBlank(message = "Employee ID không được để trống")
    private String employeeId;

    @NotNull(message = "Ngày tạo đơn hàng không được để null")
    @PastOrPresent(message = "Ngày tạo đơn hàng không thể là tương lai")
    private LocalDate orderDate;

    @PositiveOrZero(message = "Order total phải là số dương")
    private double orderTotal;

    @NotBlank(message = "Payment method không được để trống")
    private String paymentMethod;

    @NotNull(message = "Chi tiết đơn hàng không được để null")
    @Size(min = 1, message = "Đơn hàng phải có ít nhất một sản phẩm")
    @Valid
    private List<@Valid OrderDetailRequest> orderDetails;
}
