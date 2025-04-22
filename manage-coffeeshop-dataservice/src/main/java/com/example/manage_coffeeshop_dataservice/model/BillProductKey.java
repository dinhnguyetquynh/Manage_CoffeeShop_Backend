package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BillProductKey implements Serializable {
    @Column(name="bill_id")
    private int billId;
    @Column(name="product_id")
    private int productId;

}