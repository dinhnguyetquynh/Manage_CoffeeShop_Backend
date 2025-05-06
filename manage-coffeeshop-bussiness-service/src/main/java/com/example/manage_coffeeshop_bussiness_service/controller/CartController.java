package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartRes;
import com.example.manage_coffeeshop_bussiness_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<CartRes> createCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.createCart(cartRequest));
    }
    @GetMapping
    public ResponseEntity<List<CartRes>> getAllCarts(){
        return ResponseEntity.ok(cartService.getAllCarts()); }


    @GetMapping("/{id}")
    public ResponseEntity<CartRes> findByCartId(@PathVariable Integer id){
        return ResponseEntity.ok(cartService.findCartById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CartRes> updateCart(
            @PathVariable Integer id,
            @RequestBody CartRequest req){
        return ResponseEntity.ok(cartService.updateCart(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(
            @PathVariable Integer id){
        return ResponseEntity.ok(cartService.deleteCart(id));
    }
}
