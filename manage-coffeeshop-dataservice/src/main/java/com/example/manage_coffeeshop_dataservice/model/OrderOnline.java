package com.example.manage_coffeeshop_dataservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"ordDetails","transactionHistory"}) // tránh vòng lặp
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int orderOnlID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime deliveryTime;
    private String deliveryAddress;
    private String noteOfCus;
    private double totalOrd;
    private String paymentMethod;
    private String transactionCode;
    private Boolean paid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "orderOnline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOnlineDetail> ordDetails;
    @OneToOne(mappedBy = "orderOnline",cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private TransactionHistory transactionHistory;
}
