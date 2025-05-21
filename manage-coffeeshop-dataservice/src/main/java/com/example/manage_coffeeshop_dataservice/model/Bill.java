package com.example.manage_coffeeshop_dataservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Data
@NoArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate billCreationDate;
    private double billTotal;
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="employee_id",nullable = false)
    private Employee employee;

    //có 1 khóa ngoại bên bảng billDetail.
    @OneToMany(mappedBy = "bill")
    private Set<BillDetail> billDetails = new HashSet<>();


    @Override
    public int hashCode() {
        return Objects.hash(billId); // Chỉ sử dụng billId
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return billId == bill.billId; // Chỉ so sánh billId
    }
}