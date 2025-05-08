package com.example.manage_coffeeshop_bussiness_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @NotNull(message = "Mã Bill không được để trống")
    @Min(value = 1, message = "Mã Bill phải lớn hơn 0")
    private Integer billId;

    @NotBlank(message = "Mã Khách hàng không được để trống")
    @Min(value = 1, message = "Mã khách hàng phải lớn hơn 0")
    private String customerId;

    @NotBlank(message = "Mã Nhân viên không được để trống")
    @Min(value = 1, message = "Mã nhân viên phải lớn hơn 0")
    private String employeeId;

    @NotNull(message = "Ngày đặt hàng không được để trống")
    @PastOrPresent(message = "Ngày đặt hàng không được trong tương lai")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @NotNull(message = "Tổng tiền không được để trống")
    @Positive(message = "Tổng tiền phải là số dương")
    private Double orderTotal;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Pattern(regexp = "CASH|CARD|PAYPAL", message = "Phương thức thanh toán phải là CASH, CARD hoặc PAYPAL")
    private String paymentMethod;

    @NotEmpty(message = "Danh sách chi tiết đặt hàng không được để trống")
    private List<@Valid OrderDetailRequest> orderDetails;
}
