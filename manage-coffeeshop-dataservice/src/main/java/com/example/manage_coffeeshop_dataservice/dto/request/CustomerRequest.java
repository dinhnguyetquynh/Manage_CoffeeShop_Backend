package com.example.manage_coffeeshop_dataservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CustomerRequest {
    private String customerName;
    private String customerPhone;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;
    @Email
    private String email;
    private String address;
    private String accountCus;
    private String passwordCus;


}