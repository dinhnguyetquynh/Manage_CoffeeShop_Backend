package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CartRequest;
import com.example.manage_coffeeshop_dataservice.dto.request.CartToppingRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartRes;
import com.example.manage_coffeeshop_dataservice.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired private CartRepository cartRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ToppingRepository toppingRepository;
    @Autowired private DiscountCodeRepository discountCodeRepository;

    @GetMapping("/{customerId}")
    public ResponseEntity<CartRes> getOrCreateCart(@PathVariable int customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomer(customerRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Customer not found")));
                    c.setTotal(0);
                    c.setQuantity(0);
                    c.setShipCost(0);
                    c.setDiscountCode(null);
                    c.setPaymentMethod("Bank Transfer");
                    return cartRepository.save(c);
                });
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }

    @Transactional
    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartRes> addItems(
            @PathVariable int customerId,
            @RequestBody CartRequest req) {

        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // nếu lần đầu thêm item, set shipCost = 10000
        if (cart.getCartItems().isEmpty()) {
            cart.setShipCost(10000);
        }

        for (CartItemRequest cri : req.getItems()) {
            Product p = productRepository.findById(cri.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem ci = new CartItem();
            ci.setCart(cart);
            ci.setProduct(p);
            ci.setSize(cri.getSize());
            ci.setQuantity(cri.getQuantity());
            ci.setSweet(cri.getSweet());
            ci.setIce(cri.getIce());

            for (CartToppingRequest tr : cri.getToppings()) {
                Topping t = toppingRepository.findByToppingName(tr.getToppingName())
                        .orElseThrow(() -> new RuntimeException("Topping not found: " + tr.getToppingName()));
                CartItemTopping cit = new CartItemTopping(ci, t, tr.getQuantity());
                ci.getCartItemToppings().add(cit);
            }

            double base = p.getProductPrice() * cri.getQuantity();
            double toppingSum = ci.getCartItemToppings().stream()
                    .mapToDouble(cit -> cit.getTopping().getToppingPrice() * cit.getQuantity() * cri.getQuantity())
                    .sum();
            ci.setPrice(base + toppingSum);
            cart.getCartItems().add(ci);
        }

        // tính lại giỏ
        recalculateCart(cart, req.getDiscountCode(), req.getPaymentMethod());
        Cart saved = cartRepository.save(cart);
        return ResponseEntity.ok(CartRes.fromEntity(saved));
    }

    @Transactional
    @PutMapping("/{customerId}")
    public ResponseEntity<CartRes> updateCart(
            @PathVariable int customerId,
            @RequestBody CartRequest req) {

        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        recalculateCart(cart, req.getDiscountCode(), req.getPaymentMethod());
        Cart saved = cartRepository.save(cart);
        return ResponseEntity.ok(CartRes.fromEntity(saved));
    }

    @Transactional
    @PutMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartRes> updateCartItem(
            @PathVariable int customerId,
            @PathVariable Long itemId,
            @RequestBody CartItemRequest req) {

        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getCartItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // cập nhật
        item.setQuantity(req.getQuantity());
        item.setSize(req.getSize());
        item.setSweet(req.getSweet());
        item.setIce(req.getIce());
        item.getCartItemToppings().clear();
        for (CartToppingRequest tr : req.getToppings()) {
            Topping t = toppingRepository.findByToppingName(tr.getToppingName())
                    .orElseThrow(() -> new RuntimeException("Topping not found: " + tr.getToppingName()));
            CartItemTopping cit = new CartItemTopping(item, t, tr.getQuantity());
            item.getCartItemToppings().add(cit);
        }
        double base = item.getProduct().getProductPrice() * item.getQuantity();
        double toppingSum = item.getCartItemToppings().stream()
                .mapToDouble(cit -> cit.getTopping().getToppingPrice() * cit.getQuantity() * item.getQuantity())
                .sum();
        item.setPrice(base + toppingSum);

        recalculateCart(cart, cart.getDiscountCode(), cart.getPaymentMethod());
        Cart saved = cartRepository.save(cart);
        return ResponseEntity.ok(CartRes.fromEntity(saved));
    }

    @Transactional
    @DeleteMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartRes> deleteCartItem(
            @PathVariable int customerId,
            @PathVariable Long itemId) {

        cartItemRepository.deleteById(itemId);
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        recalculateCart(cart, cart.getDiscountCode(), cart.getPaymentMethod());
        Cart saved = cartRepository.save(cart);
        return ResponseEntity.ok(CartRes.fromEntity(saved));
    }

    private void recalculateCart(Cart cart, String code, String paymentMethod) {
        // nếu giỏ đã trống, shipCost về 0
        if (cart.getCartItems().isEmpty()) {
            cart.setShipCost(0);
        }

        double sumItems = cart.getCartItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();

        double discountAmount = 0;
        if (code != null && !code.isBlank()) {
            DiscountCode dc = discountCodeRepository.findByDiscountCodeName(code)
                    .orElseThrow(() -> new RuntimeException("Invalid discount code"));
            LocalDate today = LocalDate.now();
            if (today.isBefore(dc.getValidFrom()) || today.isAfter(dc.getValidUntil()))
                throw new RuntimeException("Discount code not valid today");
            if (dc.getUsageLimit() <= 0)
                throw new RuntimeException("Discount code usage limit exceeded");

            discountAmount = sumItems * (dc.getPercentOff() / 100.0);
            dc.setUsageLimit(dc.getUsageLimit() - 1);
            discountCodeRepository.save(dc);
            cart.setDiscountCode(dc.getDiscountCodeName());
        } else {
            cart.setDiscountCode(null);
        }

        cart.setTotal(sumItems - discountAmount + cart.getShipCost());
        cart.setQuantity(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
        cart.setPaymentMethod(paymentMethod);
    }
}
