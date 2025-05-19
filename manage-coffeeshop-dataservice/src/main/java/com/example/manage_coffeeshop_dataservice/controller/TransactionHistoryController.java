package com.example.manage_coffeeshop_dataservice.controller;

import com.example.manage_coffeeshop_dataservice.dto.respone.TransactionHistoryRes;
import com.example.manage_coffeeshop_dataservice.mapper.TransactionHistoryMapper;
import com.example.manage_coffeeshop_dataservice.model.TransactionHistory;
import com.example.manage_coffeeshop_dataservice.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class TransactionHistoryController {
    @Autowired
    private TransactionHistoryRepository repo;

    private TransactionHistoryMapper mapper = new TransactionHistoryMapper();

    @GetMapping("/{customerId}")
    public List<TransactionHistoryRes> getAllByCustomerId(@PathVariable int customerId){

        List<TransactionHistory> list = repo.findByCustomer_CustomerId(customerId);
        List<TransactionHistoryRes> listRes = new ArrayList<>();
        for(TransactionHistory tr:list){
            TransactionHistoryRes trRes = mapper.toTransactionHistoryRes(tr);
            listRes.add(trRes);
        }
        return listRes;

    }


}
