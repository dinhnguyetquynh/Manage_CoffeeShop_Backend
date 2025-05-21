package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.CartRequest;
import com.example.manage_coffeeshop_dataservice.dto.request.CartToppingRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.CartRes;
import com.example.manage_coffeeshop_dataservice.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartRepository cartRepository;
    //    @Autowired private CartMapper cartMapper;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ToppingRepository toppingRepository;
    @Autowired private ProductRepository productRepository;


    @GetMapping("/{customerId}")
    public ResponseEntity<CartRes> getOrCreateCart(@PathVariable int customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomer(customerRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Customer not found")));
                    c.setTotal(0);
                    c.setQuantity(0);
                    c.setShipCost(10000);
                    c.setDiscountCode("");
                    c.setPaymentMethod("Bank Transfer");
                    return cartRepository.save(c);
                });
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartRes> addItems(
            @PathVariable int customerId,
            @RequestBody CartRequest req) {

        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        for (CartItemRequest cri : req.getItems()) {
            if (cri.getProductId() == null || cri.getQuantity() == null || cri.getQuantity() <= 0) {
                continue; // Bỏ qua item không hợp lệ
            }

            Product p = productRepository.findById(cri.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem ci = new CartItem();
            ci.setCart(cart);
            ci.setProduct(p);
            ci.setSize(cri.getSize());
            ci.setQuantity(cri.getQuantity());
            ci.setSweet(cri.getSweet());
            ci.setIce(cri.getIce());
            ci.setCartItemToppings(new ArrayList<>());

            System.out.println("Cart controller ok 1");

            // Xử lý toppings nếu có
            if (cri.getToppings() != null) {
                for (CartToppingRequest tr : cri.getToppings()) {
                    if (tr.getToppingName() == null || tr.getQuantity() == null || tr.getQuantity() <= 0) {
                        continue; // Bỏ qua topping không hợp lệ
                    }

                    Topping t = toppingRepository.findByToppingName(tr.getToppingName())
                            .orElseThrow(() -> new RuntimeException("Topping not found: " + tr.getToppingName()));

                    CartItemTopping cit = new CartItemTopping(ci, t, tr.getQuantity());
                    ci.getCartItemToppings().add(cit);
                }
            }
            System.out.println("Cart controller ok 2");

            // Tính giá tiền sản phẩm: giá sản phẩm + tổng topping × số lượng
            int itemQty = cri.getQuantity();
            double base = p.getProductPrice() * itemQty;
            double toppingSum = ci.getCartItemToppings().stream()
                    .mapToDouble(cit -> cit.getTopping().getToppingPrice() * cit.getQuantity() * itemQty)
                    .sum();
            ci.setPrice(base + toppingSum);

            cart.getCartItems().add(ci);
        }
        // Tính lại tổng số lượng và tổng giá
        int qty = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        double sum = cart.getCartItems().stream()
                .mapToDouble(ci -> ci.getPrice() != null ? ci.getPrice() : 0.0)
                .sum();
        cart.setQuantity(qty);
        cart.setTotal(sum + cart.getShipCost());
        if (req.getDiscountCode() != null) {
            cart.setDiscountCode(req.getDiscountCode());
        }
        if (req.getPaymentMethod() != null) {
            cart.setPaymentMethod(req.getPaymentMethod());
        }
        cart = cartRepository.save(cart);
        System.out.println(cart);
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }



    @PutMapping("/{customerId}")
    public ResponseEntity<CartRes> updateCart(@PathVariable int customerId,
                                              @RequestBody CartRequest req) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setDiscountCode(req.getDiscountCode());
        cart.setPaymentMethod(req.getPaymentMethod());
        cart = cartRepository.save(cart);
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }

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

        // 1) Cập nhật thông tin cơ bản
        item.setQuantity(req.getQuantity());
        item.setSize(req.getSize());
        item.setSweet(req.getSweet());
        item.setIce(req.getIce());

        // 2) Xóa sạch CartItemTopping cũ
        item.getCartItemToppings().clear();

        // 3) Thêm lại CartItemTopping mới từ request
        for (CartToppingRequest tr : req.getToppings()) {
            Topping t = toppingRepository.findByToppingName(tr.getToppingName())
                    .orElseThrow(() -> new RuntimeException("Topping not found: " + tr.getToppingName()));
            CartItemTopping cit = new CartItemTopping(item, t, tr.getQuantity());
            item.getCartItemToppings().add(cit);
        }

        // 4) Tính lại price của item
        double base = item.getProduct().getProductPrice() * item.getQuantity();
        double toppingSum = item.getCartItemToppings().stream()
                .mapToDouble(cit -> cit.getTopping().getToppingPrice() * cit.getQuantity() * item.getQuantity())
                .sum();
        item.setPrice(base + toppingSum);

        // 5) Save cart mới
        cartRepository.save(cart);

        // 6) Trả về CartRes
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }


    @DeleteMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartRes> deleteCartItem(
            @PathVariable int customerId,
            @PathVariable Long itemId) {
        cartItemRepository.deleteById(itemId);
        Cart cart = cartRepository.findByCustomerCustomerId(customerId).get();
        return ResponseEntity.ok(CartRes.fromEntity(cart));
    }


}
