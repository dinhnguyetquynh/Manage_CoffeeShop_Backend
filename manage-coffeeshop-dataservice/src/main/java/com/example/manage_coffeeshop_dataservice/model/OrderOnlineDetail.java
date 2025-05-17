package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOnlineDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailID;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private String size;
    private double unitPrice;
    private int quantity;
    private String sweet;
    private String ice;

    @OneToMany(mappedBy = "orderOnlineDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOnlineDetailTopping> orderOnlineDetailToppings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="ordOnl_id")
    private OrderOnline orderOnline;


}
