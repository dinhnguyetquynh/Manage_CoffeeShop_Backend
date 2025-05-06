package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartItemRes;
import com.example.manage_coffeeshop_bussiness_service.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemRes> createCartItem(
            @Valid @RequestBody CartItemRequest req) {
        CartItemRes created = cartItemService.createCartItem(req);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<CartItemRes>> getAllCartItems() {
        List<CartItemRes> list = cartItemService.getAllCartItems();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemRes> getCartItemById(@PathVariable Long id) {
        CartItemRes item = cartItemService.findCartItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemRes> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody CartItemRequest req) {
        CartItemRes updated = cartItemService.updateCartItem(id, req);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        return cartItemService.deleteCartItem(id);
    }

    @PostMapping("/{cartItemId}/toppings")
    public ResponseEntity<CartItemRes> addToppings(
            @PathVariable Long cartItemId,
            @RequestBody List<Long> toppingIds) {
        return ResponseEntity.ok(
                cartItemService.addToppingsToCartItem(cartItemId, toppingIds)
        );
    }
}
