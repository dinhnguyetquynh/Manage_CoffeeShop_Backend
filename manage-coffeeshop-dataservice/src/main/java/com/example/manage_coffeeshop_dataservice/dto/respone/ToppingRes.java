package com.example.manage_coffeeshop_dataservice.dto.respone;

import lombok.Data;

import com.example.manage_coffeeshop_dataservice.model.Topping;

@Data
public class ToppingRes {
    private Integer toppingID;
    private String toppingName;
    private Double toppingPrice;

    public static ToppingRes fromEntity(Topping topping) {
        ToppingRes toppingRes = new ToppingRes();
        toppingRes.toppingID = topping.getToppingID();
        toppingRes.toppingName = topping.getToppingName();
        toppingRes.toppingPrice = topping.getToppingPrice();
        return toppingRes;
    }
}