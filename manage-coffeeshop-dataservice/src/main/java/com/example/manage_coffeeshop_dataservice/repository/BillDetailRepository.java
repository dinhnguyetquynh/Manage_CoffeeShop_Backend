package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.BillDetail;
import com.example.manage_coffeeshop_dataservice.model.BillProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, BillProductKey> {
    List<BillDetail> findAllByIdBillId(int billId);
}