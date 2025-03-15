package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.BillDetailRequest;
import com.example.manage_coffeeshop_dataservice.model.Bill;
import com.example.manage_coffeeshop_dataservice.model.BillDetail;
import com.example.manage_coffeeshop_dataservice.model.BillProductKey;
import com.example.manage_coffeeshop_dataservice.model.Product;
import com.example.manage_coffeeshop_dataservice.repository.BillDetailRepository;
import com.example.manage_coffeeshop_dataservice.repository.BillRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bill-details")
public class BillDetailController {
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    // CREATE new bill detail
    @PostMapping
    public ResponseEntity<BillDetail> createBillDetail(@RequestBody BillDetailRequest request) {
        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BillProductKey id = new BillProductKey();
        id.setBillId(request.getBillId());
        id.setProductId(request.getProductId());

        BillDetail billDetail = new BillDetail();
        billDetail.setId(id);
        billDetail.setBill(bill);
        billDetail.setProduct(product);
        billDetail.setProductQuantity(request.getProductQuantity());
        billDetail.setSubTotal(request.getSubTotal());

        BillDetail savedDetail = billDetailRepository.save(billDetail);
        return ResponseEntity.ok(savedDetail);
    }

    // DELETE bill detail
    @DeleteMapping
    public ResponseEntity<?> deleteBillDetail(@RequestBody BillProductKey id) {
        return billDetailRepository.findById(id)
                .map(detail -> {
                    billDetailRepository.delete(detail);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}