package com.example.manage_coffeeshop_dataservice.repository;

import com.example.manage_coffeeshop_dataservice.model.Topping;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Integer> {
    boolean existsByToppingName(String name);
    Optional<Topping> findByToppingName(String name);
}
