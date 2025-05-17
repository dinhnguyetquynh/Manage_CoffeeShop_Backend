package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.DiscountCodeRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.DiscountCodeRes;
import com.example.manage_coffeeshop_dataservice.mapper.DiscountCodeMapper;
import com.example.manage_coffeeshop_dataservice.model.DiscountCode;
import com.example.manage_coffeeshop_dataservice.repository.DiscountCodeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
@Validated
public class DiscountCodeController {
    private final DiscountCodeRepository discountCodeRepository;
    private final DiscountCodeMapper discountCodeMapper;

    @GetMapping
    public List<DiscountCodeRes> listAll() {
        return discountCodeRepository.findAll().stream()
                .map(dc -> {
                    DiscountCodeRes dto = new DiscountCodeRes();
                    dto.setDiscountCodeId(dc.getDiscountCodeId());
                    dto.setDiscountCodeName(dc.getDiscountCodeName());
                    dto.setPercentOff(dc.getPercentOff());
                    dto.setValidFrom(dc.getValidFrom());
                    dto.setValidUntil(dc.getValidUntil());
                    dto.setUsageLimit(dc.getUsageLimit());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public ResponseEntity<DiscountCodeRes> getByCode(@PathVariable String code) {
        return discountCodeRepository.findByDiscountCodeName(code)
                .map(dc -> {
                    DiscountCodeRes dto = new DiscountCodeRes();
                    dto.setDiscountCodeId(dc.getDiscountCodeId());
                    dto.setDiscountCodeName(dc.getDiscountCodeName());
                    dto.setPercentOff(dc.getPercentOff());
                    dto.setValidFrom(dc.getValidFrom());
                    dto.setValidUntil(dc.getValidUntil());
                    dto.setUsageLimit(dc.getUsageLimit());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DiscountCodeRes> create(@Valid @RequestBody DiscountCodeRequest req) {
        DiscountCode dc = DiscountCode.builder()
                .discountCodeName(req.getDiscountCodeName())
                .percentOff(req.getPercentOff())
                .validFrom(req.getValidFrom())
                .validUntil(req.getValidUntil())
                .usageLimit(req.getUsageLimit())
                .build();
        DiscountCode saved = discountCodeRepository.save(dc);
        // manual mapping
        DiscountCodeRes res = new DiscountCodeRes();
        res.setDiscountCodeId(saved.getDiscountCodeId());
        res.setDiscountCodeName(saved.getDiscountCodeName());
        res.setPercentOff(saved.getPercentOff());
        res.setValidFrom(saved.getValidFrom());
        res.setValidUntil(saved.getValidUntil());
        res.setUsageLimit(saved.getUsageLimit());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCodeRes> update(
            @PathVariable Integer id,
            @Valid @RequestBody DiscountCodeRequest req) {

        return discountCodeRepository.findById(id)
                .map(existing -> {
                    discountCodeMapper.updateFromReq(req, existing);
                    return ResponseEntity.ok(discountCodeMapper.toDiscountCodeRes(discountCodeRepository.save(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public String deleteDiscountCode(@PathVariable Integer id) {
        try {
            discountCodeRepository.deleteById(id);
            return "Discount code deleted successfully";
        } catch (RuntimeException ex) {
            throw new RuntimeException("Failed to Delete DiscountCode", ex);
        }
    }
}