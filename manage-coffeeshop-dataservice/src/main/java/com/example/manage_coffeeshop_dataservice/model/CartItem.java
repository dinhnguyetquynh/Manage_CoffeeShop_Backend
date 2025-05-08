package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartitem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cart", "product", "toppings"})
@EqualsAndHashCode(exclude = {"cart", "product", "toppings"})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    private String size;
    private Double price;
    private Integer quantity;
    private String sweet;
    private String ice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemTopping> cartItemToppings = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "cartitem_topping",
//            joinColumns = @JoinColumn(name = "cartitem_id"),
//            inverseJoinColumns = @JoinColumn(name = "topping_id")
//    )
//    private List<Topping> toppings = new ArrayList<>();
}
