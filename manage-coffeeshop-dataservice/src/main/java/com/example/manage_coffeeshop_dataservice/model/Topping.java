package com.example.manage_coffeeshop_dataservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cartItems"}) // tránh vòng lặp khi log
@EqualsAndHashCode(exclude = {"cartItems"}) // tránh lỗi khi so sánh object
public class Topping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toppingid")
    private int toppingID;

    private String toppingName;
    private double toppingPrice;

    @OneToMany(mappedBy = "topping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemTopping> cartItemToppings = new ArrayList<>();

    @OneToMany(mappedBy = "topping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOnlineDetailTopping> orderOnlineDetailToppings = new ArrayList<>();

//    @ManyToMany(mappedBy = "toppings")
//    private List<CartItem> cartItems = new ArrayList<>();
}
