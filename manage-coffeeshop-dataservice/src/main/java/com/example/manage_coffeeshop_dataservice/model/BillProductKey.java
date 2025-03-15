package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BillProductKey implements Serializable {
    @Column(name="bill_id")
    private int billId;
    @Column(name="product_id")
    private int productId;

}