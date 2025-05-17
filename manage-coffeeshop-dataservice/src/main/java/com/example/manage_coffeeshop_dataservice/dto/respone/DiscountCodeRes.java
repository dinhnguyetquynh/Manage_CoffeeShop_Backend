package com.example.manage_coffeeshop_dataservice.dto.respone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DiscountCodeRes {
    private Integer discountCodeId;
    private String discountCodeName;
    private Double percentOff;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
    private Integer usageLimit;
}
