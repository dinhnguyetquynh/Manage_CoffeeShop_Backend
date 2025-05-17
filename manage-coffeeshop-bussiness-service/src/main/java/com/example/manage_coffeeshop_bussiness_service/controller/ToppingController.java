package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ToppingRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ToppingRes;
import com.example.manage_coffeeshop_bussiness_service.service.ToppingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/business/toppings")
@RequiredArgsConstructor
public class ToppingController {

    private final ToppingService toppingService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ToppingRes> createTopping(@Valid @RequestBody ToppingRequest req) {
        ToppingRes created = toppingService.createTopping(req);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ToppingRes>> getAllToppings() {
        List<ToppingRes> list = toppingService.getAllToppings();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ToppingRes> getToppingById(@PathVariable Long id) {
        ToppingRes t = toppingService.findToppingById(id);
        return ResponseEntity.ok(t);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ToppingRes> updateTopping(
            @PathVariable Long id,
            @Valid @RequestBody ToppingRequest req) {
        ToppingRes updated = toppingService.updateTopping(id, req);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteTopping(@PathVariable Long id) {
        return toppingService.deleteTopping(id);
    }
}