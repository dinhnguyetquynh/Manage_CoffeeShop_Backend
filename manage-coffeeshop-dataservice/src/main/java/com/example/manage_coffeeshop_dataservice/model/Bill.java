package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="employee_id",nullable = false)
    private Employee employee;


    private LocalDate billCreationDate;
    private double billTotal;
    private String paymentMethod;

    //có 1 khóa ngoại bên bảng billDetail.
    @OneToMany(mappedBy = "bill")
    private Set<BillDetail> billDetails = new HashSet<>();

    public void setCreatedAt(LocalDateTime localDateTime) {
    }
}
