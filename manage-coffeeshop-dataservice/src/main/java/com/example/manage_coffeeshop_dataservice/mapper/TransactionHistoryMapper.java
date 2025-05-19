package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.respone.TransactionHistoryRes;
import com.example.manage_coffeeshop_dataservice.model.TransactionHistory;

public class TransactionHistoryMapper {
    public TransactionHistoryRes toTransactionHistoryRes(TransactionHistory trans){
        TransactionHistoryRes res = new TransactionHistoryRes();
        res.setId(trans.getId());
        res.setCustomerId(trans.getCustomer().getCustomerId());
        res.setDate(trans.getDate());
        res.setTime(trans.getTime());
        res.setOrderId(trans.getOrderOnline().getOrderOnlID());
        res.setPlusPoint(trans.getPlusPoint());
        return res;
    }
}
