package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"ordDetails"}) // tránh vòng lặp
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int orderOnlID;
    private LocalTime deliveryTime;
    private String deliveryAddress;
    private String noteOfCus;
    private double totalOrd;
    private String paymentMethod;
    private String transactionCode;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "orderOnline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOnlineDetail> ordDetails;
}
