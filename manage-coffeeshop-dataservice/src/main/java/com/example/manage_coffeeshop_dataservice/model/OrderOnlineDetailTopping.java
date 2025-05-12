package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_online_detail_topping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOnlineDetailTopping {

    @EmbeddedId
    private OrderOnlineDetailToppingKey id;

    @ManyToOne
    @MapsId("orderOnlineDetailId") // ánh xạ theo key
    @JoinColumn(name = "order_detail_id")
    private OrderOnlineDetail orderOnlineDetail;

    @ManyToOne
    @MapsId("toppingId")
    @JoinColumn(name = "topping_id")
    private Topping topping;

    private int quantity;
}
