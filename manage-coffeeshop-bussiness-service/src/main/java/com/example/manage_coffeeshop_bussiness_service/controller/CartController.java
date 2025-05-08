package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartRes;
import com.example.manage_coffeeshop_bussiness_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/business/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired private CartService service;


    @GetMapping("/{customerId}")
    public ResponseEntity<CartRes> getCart(@PathVariable int customerId) {
        return ResponseEntity.ok(service.getOrCreateCart(customerId));
    }


    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartRes> addItem(@PathVariable int customerId,
                                           @Valid @RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(service.addToCart(customerId, cartRequest));
    }


    @PutMapping("/{customerId}")
    public ResponseEntity<CartRes> updateCart(@PathVariable int customerId,
                                              @RequestBody CartRequest req) {
        return ResponseEntity.ok(service.updateCart(customerId, req));
    }


    @PutMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartRes> updateItem(@PathVariable int customerId,
                                              @PathVariable Long itemId,
                                              @RequestBody CartItemRequest req) {
        return ResponseEntity.ok(service.updateCartItem(customerId, itemId, req));
    }


    @DeleteMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartRes> deleteItem(@PathVariable int customerId,
                                              @PathVariable Long itemId) {
        return ResponseEntity.ok(service.deleteCartItem(customerId, itemId));
    }
}