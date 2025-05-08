package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartItemToppingKey implements Serializable {
    @Column
    private Long cartItemId;
    @Column
    private Integer toppingID;
}
