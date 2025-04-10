package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill findByBillCreationDate(LocalDate billCreationDate);
}