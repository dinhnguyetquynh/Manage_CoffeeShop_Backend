package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.OrderOnlineDetailTopping;
import com.example.manage_coffeeshop_dataservice.model.OrderOnlineDetailToppingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOnlineDetailToppingRepository extends JpaRepository<OrderOnlineDetailTopping, OrderOnlineDetailToppingKey> {
}
