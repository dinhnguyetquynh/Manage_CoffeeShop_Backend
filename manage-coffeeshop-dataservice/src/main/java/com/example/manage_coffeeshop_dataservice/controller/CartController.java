package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CartRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartRes;
import com.example.manage_coffeeshop_dataservice.mapper.CartMapper;
import com.example.manage_coffeeshop_dataservice.model.Cart;
import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.repository.CartItemRepository;
import com.example.manage_coffeeshop_dataservice.repository.CartRepository;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired private CartRepository cartRepository;
    @Autowired private CartMapper cartMapper;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CartItemRepository cartItemRepository;

    @PostMapping
    public CartRes createCart(@RequestBody CartRequest cartRequest) {
        Customer customer = customerRepository.findById(cartRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with ID = " + cartRequest.getCustomerId()));
        Cart cart = cartMapper.toCart(cartRequest);
        cart.setCustomer(customer);
        Cart saved = cartRepository.save(cart);
        return cartMapper.toCartRes(saved);
    }

    @GetMapping
    public List<CartRes> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toCartRes)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartRes> findCartById(@PathVariable Integer id) {
        return cartRepository.findById(id)
                .map(cartMapper::toCartRes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartRes> updateCart(
            @PathVariable Integer id,
            @RequestBody CartRequest req) {
        return cartRepository.findById(id).map(existing -> {

            existing.setTotal(req.getTotal());
            existing.setQuantity(req.getQuantity());
            existing.setShipCost(req.getShipCost());
            existing.setDiscountCode(req.getDiscountCode());
            existing.setPaymentMethod(req.getPaymentMethod());
            Cart saved = cartRepository.save(existing);
            return ResponseEntity.ok(cartMapper.toCartRes(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer id) {
        return cartRepository.findById(id).map(cart -> {
            cartItemRepository.deleteAllByCart(cart);
            cartRepository.delete(cart);
            return ResponseEntity.ok("Deleted Cart Successfully");
        }).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

}
