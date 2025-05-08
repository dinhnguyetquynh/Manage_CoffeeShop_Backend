package com.example.manage_coffeeshop_dataservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BillRequest {
    @Min(value = 1, message = "customerId phải lớn hơn 0")
    private int customerId;

    @Min(value = 1, message = "employeeId phải lớn hơn 0")
    private int employeeId;

    @NotNull(message = "billCreationDate không được để trống")
    @PastOrPresent(message = "ngày không được trong tương lai")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate billCreationDate;

    @Positive(message = "billTotal phải là số dương")
    private double billTotal;

    @NotBlank(message = "paymentMethod không được để trống")
    @Pattern(regexp = "CASH|CARD|PAYPAL", message = "Phương thức thanh toán không hợp lệ")
    private String paymentMethod;
//    private Integer customerId;
//    private Integer employeeId;
//    private LocalDateTime billCreationDate;
//    private double billTotal;
//    private String paymentMethod;
}