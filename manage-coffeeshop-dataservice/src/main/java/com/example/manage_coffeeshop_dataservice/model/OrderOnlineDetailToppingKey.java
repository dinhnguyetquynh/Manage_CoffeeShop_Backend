package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOnlineDetailToppingKey implements Serializable {
    private int orderOnlineDetailId;
    private int toppingId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderOnlineDetailToppingKey)) return false;
        OrderOnlineDetailToppingKey that = (OrderOnlineDetailToppingKey) o;
        return orderOnlineDetailId == that.orderOnlineDetailId &&
                toppingId == that.toppingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderOnlineDetailId, toppingId);
    }
}
