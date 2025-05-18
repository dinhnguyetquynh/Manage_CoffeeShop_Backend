package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.OrderOnlineDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOnlineDetailRepository extends JpaRepository<OrderOnlineDetail,Integer> {
}
