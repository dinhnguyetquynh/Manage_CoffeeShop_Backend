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

    @NotNull(message = "customerId không được để trống")
    @Min(value = 1, message = "customerId phải lớn hơn 0")
    private Integer customerId;

    @NotNull(message = "employeeId không được để trống")
    @Min(value = 1, message = "employeeId phải lớn hơn 0")
    private Integer employeeId;

    @NotNull(message = "Ngày tạo hóa đơn không được để trống")
    @PastOrPresent(message = "ngày tạo hóa đơn phải là ngày hiện tại")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate billCreationDate;

    @NotNull(message = "Tổng tiền không được để trống")
    @Positive(message = "Tổng tiền phải là số dương")
    private Double billTotal;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Pattern(regexp = "CASH|CARD|PAYPAL", message = "Phương thức thanh toán phải là CASH, CARD hoặc PAYPAL")
    private String paymentMethod;
}