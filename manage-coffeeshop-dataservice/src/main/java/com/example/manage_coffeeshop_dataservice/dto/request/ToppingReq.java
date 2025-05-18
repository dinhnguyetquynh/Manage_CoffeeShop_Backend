package com.example.manage_coffeeshop_dataservice.dto.request;

import lombok.Data;

@Data
public class ToppingReq {
    private String toppingName;
    private int quantity;
}
