package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartItemRes;
import com.example.manage_coffeeshop_dataservice.mapper.CartItemMapper;
import com.example.manage_coffeeshop_dataservice.model.Cart;
import com.example.manage_coffeeshop_dataservice.model.CartItem;
import com.example.manage_coffeeshop_dataservice.model.Product;
import com.example.manage_coffeeshop_dataservice.model.Topping;
import com.example.manage_coffeeshop_dataservice.repository.CartItemRepository;
import com.example.manage_coffeeshop_dataservice.repository.CartRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import com.example.manage_coffeeshop_dataservice.repository.ToppingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @PostMapping
    public ResponseEntity<CartItemRes> createCartItem(@RequestBody CartItemRequest req) {
        Cart cart = cartRepository.findById(req.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemMapper.toCartItem(req);
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        // Xử lý toppings
        if (req.getToppingIds() != null) {
            List<Topping> toppings = toppingRepository.findAllById(req.getToppingIds());
            cartItem.getToppings().addAll(toppings);
        }

        CartItem saved = cartItemRepository.save(cartItem);
        return ResponseEntity.ok(cartItemMapper.toCartItemRes(saved));
    }

    @GetMapping
    public List<CartItemRes> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItemMapper::toCartItemRes)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemRes> findCartItemById(@PathVariable Long id) {
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toCartItemRes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemRes> updateCartItem(
            @PathVariable Long id,
            @RequestBody CartItemRequest req) {

        return cartItemRepository.findById(id).map(existing -> {
            existing.setSize(req.getSize());
            existing.setPrice(req.getPrice());
            existing.setQuantity(req.getQuantity());
            existing.setSweet(req.getSweet());
            existing.setIce(req.getIce());

            List<Topping> toppings = toppingRepository.findAllById(req.getToppingIds());
            existing.getToppings().clear();
            existing.getToppings().addAll(toppings);

            CartItem saved = cartItemRepository.save(existing);
            return ResponseEntity.ok(cartItemMapper.toCartItemRes(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        try{
            cartItemRepository.deleteById(id);
            return "Deleted CartItem Successfully";
        } catch (RuntimeException ex) {
            throw new RuntimeException("Failed to delete CartItem", ex);
        }
    }


}
