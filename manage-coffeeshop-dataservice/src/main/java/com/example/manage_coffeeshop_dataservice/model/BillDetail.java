package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BillDetail {
    @EmbeddedId
    private BillProductKey id = new BillProductKey();

    //dùng để map quan hệ 1-1 với bill, truường billId của bảng bill vừa là khóa chính, vừa là khóa ngoại
    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name="bill_id")
    private Bill bill;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    private Product product;

    private int productQuantity;
    private double subTotal;






}
