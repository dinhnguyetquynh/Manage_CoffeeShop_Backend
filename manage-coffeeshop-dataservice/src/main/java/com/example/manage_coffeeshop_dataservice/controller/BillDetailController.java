package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.request.BillDetailRequest;
import com.example.manage_coffeeshop_dataservice.dto.respone.BillDetailRes;
import com.example.manage_coffeeshop_dataservice.mapper.BillDetailMapper;
import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.BillDetailRepository;
import com.example.manage_coffeeshop_dataservice.repository.BillRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bill-details")
public class BillDetailController {
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillDetailMapper billDetailMapper;

    @GetMapping
    public List<BillDetailRes> getAllBillDetails() {
        return billDetailRepository.findAll().stream()
                .map(billDetailMapper::toBillDetailRes)
                .collect(Collectors.toList());
    }

    @GetMapping("/bill/{billId}")
    public List<BillDetailRes> getDetailsByBillId(@PathVariable int billId) {
        return billDetailRepository.findAllByIdBillId(billId).stream()
                .map(this::convertToRes)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<BillDetailRes> create(@RequestBody @Valid BillDetailRequest req) {
        BillProductKey key = new BillProductKey(req.getBillId(), req.getProductId());
        if (billDetailRepository.existsById(key)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        Bill bill = billRepository.findById(req.getBillId())
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        Product prod = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        BillDetail d = new BillDetail();
        d.setId(key);
        d.setBill(bill);
        d.setProduct(prod);
        d.setProductQuantity(req.getProductQuantity());
        d.setSubTotal(prod.getProductPrice() * req.getProductQuantity());
        BillDetail saved = billDetailRepository.save(d);
        BillDetailRes res = billDetailMapper.toBillDetailRes(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping
    public ResponseEntity<BillDetailRes> updateDetail(@RequestBody BillDetailRequest request) {
        BillProductKey key = new BillProductKey(request.getBillId(), request.getProductId());
        return billDetailRepository.findById(key)
                .map(existingDetail -> {
                    Product product = productRepository.findById(request.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    existingDetail.setProductQuantity(request.getProductQuantity());
                    existingDetail.setSubTotal(product.getProductPrice() * request.getProductQuantity());

                    BillDetail updatedDetail = billDetailRepository.save(existingDetail);
                    return ResponseEntity.ok(convertToRes(updatedDetail));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<String> deleteDetail(@RequestParam int billId,
                                               @RequestParam int productId) {
        BillProductKey key = new BillProductKey(billId, productId);
        return billDetailRepository.findById(key)
                .map(detail -> {
                    billDetailRepository.delete(detail);
                    return ResponseEntity
                            .ok("Xóa thành công");
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chi tiết hóa đơn"));
    }


    private BillDetailRes convertToRes(BillDetail detail) {
        BillDetailRes res = new BillDetailRes();
        res.setBillId(detail.getId().getBillId());
        res.setProductId(detail.getId().getProductId());
        res.setProductQuantity(detail.getProductQuantity());
        res.setSubTotal(detail.getSubTotal());
        return res;
    }
}