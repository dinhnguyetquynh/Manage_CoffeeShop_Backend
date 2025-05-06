package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Customer {
    //hoten,sdt,  gioitinh, ngaysinh,email,diachi, rank, diem tich luy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String customerName;
    private String customerPhone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthday;
    private String email;
    private String address;
    private int accumulatedPoint;
    @Enumerated(EnumType.STRING)
    @Column(name="rank_level")
    private Rank rank_level;

    private String accountCus;
    private String passwordCus;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bill> bills = new ArrayList<>();

    public Customer(int customerId, String customerName, String customerPhone, Gender gender, LocalDate birthday, String email, String address, int accumulatedPoint, Rank rank_level, String accountCus, String passwordCus) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.accumulatedPoint = accumulatedPoint;
        this.rank_level = rank_level;
        this.accountCus = accountCus;
        this.passwordCus = passwordCus;
    }

    public Customer(int customerId, String customerName, String customerPhone, Gender gender, LocalDate birthday, String email, String address, int accumulatedPoint, Rank rank_level, String accountCus, String passwordCus, List<Bill> bills) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.accumulatedPoint = accumulatedPoint;
        this.rank_level = rank_level;
        this.accountCus = accountCus;
        this.passwordCus = passwordCus;
        this.bills = bills;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId;
    }
}