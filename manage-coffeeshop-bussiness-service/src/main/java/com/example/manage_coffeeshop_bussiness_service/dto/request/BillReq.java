package com.example.manage_coffeeshop_bussiness_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillReq {

    @Min(value = 1, message = "customerId phải lớn hơn 0")
    private int customerId;

    @Min(value = 1, message = "employeeId phải lớn hơn 0")
    private int employeeId;

    @NotNull(message = "billCreationDate không được để trống")
    @PastOrPresent(message = "ngày không được trong tương lai")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate billCreationDate;

    @Positive(message = "billTotal phải là số dương")
    private double billTotal;

    @NotBlank(message = "paymentMethod không được để trống")
    @Pattern(regexp = "CASH|CARD|PAYPAL", message = "Phương thức thanh toán không hợp lệ")
    private String paymentMethod;
}