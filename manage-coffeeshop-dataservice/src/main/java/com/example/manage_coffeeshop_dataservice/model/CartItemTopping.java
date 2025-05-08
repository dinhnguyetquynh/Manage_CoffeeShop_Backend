package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartitem_topping")
public class CartItemTopping {
    @EmbeddedId
    private CartItemToppingKey id;

    @ManyToOne
    @MapsId("cartItemId")
    @JoinColumn(name = "cartitem_id")
    private CartItem cartItem;

    @ManyToOne
    @MapsId("toppingID")
    @JoinColumn(name = "topping_id")
    private Topping topping;

    private Integer quantity;

    public CartItemTopping(CartItem cartItem, Topping topping, Integer quantity) {
        this.cartItem = cartItem;
        this.topping = topping;
        this.quantity = quantity;
        // khởi tạo id dựa trên các khóa
        this.id = new CartItemToppingKey(
                cartItem.getCartItemId(),
                topping.getToppingID()
        );
    }
}
