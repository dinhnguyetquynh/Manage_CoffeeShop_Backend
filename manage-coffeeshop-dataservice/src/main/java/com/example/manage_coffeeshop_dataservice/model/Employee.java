package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    private String empName;
    private int empYearOfBirth;
    private String empPhone;
    private int empRole;
    private String empAccount;
    private String empPassword;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bill> bills;

    public Employee(int empId, String empName, int empYearOfBirth, String empPhone, int empRole, String empAccount, String empPassword) {
        this.empId = empId;
        this.empName = empName;
        this.empYearOfBirth = empYearOfBirth;
        this.empPhone = empPhone;
        this.empRole = empRole;
        this.empAccount = empAccount;
        this.empPassword = empPassword;
    }
}
