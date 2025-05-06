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
    private Long toppingID;

    private String toppingName;
    private Double toppingPrice;

    @ManyToMany(mappedBy = "toppings")
    private List<CartItem> cartItems = new ArrayList<>();
}
