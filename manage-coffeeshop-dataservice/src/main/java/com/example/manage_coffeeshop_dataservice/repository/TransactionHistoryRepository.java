package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.Bill;
import com.example.manage_coffeeshop_dataservice.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    List<TransactionHistory> findByCustomer_CustomerId(int customerId);
}
