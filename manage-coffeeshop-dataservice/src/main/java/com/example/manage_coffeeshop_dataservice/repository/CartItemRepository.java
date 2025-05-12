package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.Cart;
import com.example.manage_coffeeshop_dataservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);
}
