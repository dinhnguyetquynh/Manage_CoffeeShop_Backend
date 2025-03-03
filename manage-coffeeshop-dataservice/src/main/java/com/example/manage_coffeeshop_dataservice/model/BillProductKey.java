package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BillProductKey implements Serializable {
    @Column(name="bill_id")
    private int billId;
    @Column(name="product_id")
    private int productId;

}
