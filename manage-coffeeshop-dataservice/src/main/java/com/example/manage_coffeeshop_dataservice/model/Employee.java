package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bill> bills = new ArrayList<>();

    public Employee(int empId, String empName, int empYearOfBirth, String empPhone, int empRole, String empAccount, String empPassword) {
        this.empId = empId;
        this.empName = empName;
        this.empYearOfBirth = empYearOfBirth;
        this.empPhone = empPhone;
        this.empRole = empRole;
        this.empAccount = empAccount;
        this.empPassword = empPassword;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId == employee.empId;
    }
}