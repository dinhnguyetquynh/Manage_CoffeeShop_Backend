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
@ToString(exclude = {"cartItems"})
@EqualsAndHashCode(exclude = {"cartItems"})
public class Topping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toppingID;

    private String toppingName;
    private double toppingPrice;

    @OneToMany(mappedBy = "topping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemTopping> cartItemToppings = new ArrayList<>();
    
}
