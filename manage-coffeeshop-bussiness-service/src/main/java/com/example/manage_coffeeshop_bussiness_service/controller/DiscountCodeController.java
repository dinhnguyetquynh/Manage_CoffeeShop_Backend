package com.example.manage_coffeeshop_bussiness_service.controller;

import com.example.manage_coffeeshop_bussiness_service.dto.request.DiscountCodeRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.DiscountCodeRes;
import com.example.manage_coffeeshop_bussiness_service.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/discounts")
@RequiredArgsConstructor
@Validated
public class DiscountCodeController {
    private final DiscountService discountService;

    @GetMapping
    public List<DiscountCodeRes> getAll() {
        return discountService.getAllDiscountCodes();
    }

    @GetMapping("/{code}")
    public ResponseEntity<DiscountCodeRes> getByCode(@PathVariable String code) {
        DiscountCodeRes d = discountService.findByDiscountCode(code);
        return d!=null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public DiscountCodeRes create(@Valid @RequestBody DiscountCodeRequest req) {
        return discountService.createDiscount(req);
    }

    @PutMapping("/{id}")
    public DiscountCodeRes update(
            @PathVariable Integer id,
            @Valid @RequestBody DiscountCodeRequest req) {
        return discountService.updateDiscount(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteDiscount(@PathVariable Integer id) {
        return discountService.deleteDiscount(id);
    }
}
