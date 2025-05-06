package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.ToppingRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.ToppingRes;
import com.example.manage_coffeeshop_dataservice.mapper.ToppingMapper;
import com.example.manage_coffeeshop_dataservice.model.Topping;
import com.example.manage_coffeeshop_dataservice.repository.ToppingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
public class ToppingController {
    @Autowired private ToppingRepository toppingRepository;
    @Autowired private ToppingMapper toppingMapper;

    @PostMapping
    public ToppingRes create(@Valid @RequestBody ToppingRequest toppingRequest) {
        Topping topping = toppingMapper.toTopping(toppingRequest);
        Topping saved = toppingRepository.save(topping);
        return toppingMapper.toToppingRes(saved);
    }

    @GetMapping
    public List<ToppingRes> getAllToppings() {
        return toppingRepository.findAll().stream()
                .map(toppingMapper::toToppingRes)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToppingRes> findToppingById(@PathVariable Long id) {
        return toppingRepository.findById(id)
                .map(toppingMapper::toToppingRes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToppingRes> updateTopping(
            @PathVariable Long id,
            @Valid @RequestBody ToppingRequest req) {
        return toppingRepository.findById(id).map(existing -> {
            existing.setToppingName(req.getToppingName());
            existing.setToppingPrice(req.getToppingPrice());
            Topping saved = toppingRepository.save(existing);
            return ResponseEntity.ok(toppingMapper.toToppingRes(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String deleteTopping(@PathVariable Long id) {
        return toppingRepository.findById(id).map(topping -> {
            if (!topping.getCartItems().isEmpty()) {
                throw new RuntimeException("Topping is in use and cannot be deleted");
            }
            toppingRepository.delete(topping);
            return "Deleted topping Successfully";
        }).orElse("Topping not found");
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsToppingByName(@RequestParam String name) {
        boolean exists = toppingRepository.existsByToppingName(name);
        return ResponseEntity.ok(exists);
    }
}
