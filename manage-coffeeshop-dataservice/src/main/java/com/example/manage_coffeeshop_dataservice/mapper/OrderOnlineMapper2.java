package com.example.manage_coffeeshop_dataservice.mapper;

import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineDetailRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.OrderOnlineRes;
import com.example.manage_coffeeshop_dataservice.dto.respone.ToppingRes;
import com.example.manage_coffeeshop_dataservice.model.OrderOnline;
import com.example.manage_coffeeshop_dataservice.model.OrderOnlineDetail;
import com.example.manage_coffeeshop_dataservice.model.OrderOnlineDetailTopping;

import java.util.ArrayList;
import java.util.List;

public class OrderOnlineMapper2 {
    public OrderOnlineRes toOnlineRes(OrderOnline ord){
        OrderOnlineRes orderRes = new OrderOnlineRes();
        orderRes.setOrderOnlID(ord.getOrderOnlID());
        orderRes.setDeliveryTime(ord.getDeliveryTime());
        orderRes.setDeliveryAddress(ord.getDeliveryAddress());
        orderRes.setNoteOfCus(ord.getNoteOfCus());
        orderRes.setTotalOrd(ord.getTotalOrd());
        orderRes.setPaymentMethod(ord.getPaymentMethod());
        orderRes.setTransactionCode(ord.getTransactionCode());
        orderRes.setCustomerId(ord.getCustomer().getCustomerId());
        orderRes.setPaid(ord.getPaid());
        orderRes.setOrderDate(ord.getOrderDate());

        List<OrderOnlineDetailRes> listDetailRes = new ArrayList<>();
        for (OrderOnlineDetail ordDetail : ord.getOrdDetails()) {
            OrderOnlineDetailRes ordDetailRes = new OrderOnlineDetailRes();
            ordDetailRes.setOrderDetailID(ordDetail.getOrderDetailID());
            ordDetailRes.setProductId(ordDetail.getProduct().getProductId());
            ordDetailRes.setUnitPrice(ordDetail.getUnitPrice());
            ordDetailRes.setSweet(ordDetail.getSweet());
            ordDetailRes.setSize(ordDetail.getSize());
            ordDetailRes.setIce(ordDetail.getIce());
            ordDetailRes.setQuantity(ordDetail.getQuantity());

            List<ToppingRes> listToppingRes = new ArrayList<>();
            List<OrderOnlineDetailTopping> listTopping = ordDetail.getOrderOnlineDetailToppings();
            if (listTopping != null) {
                for (OrderOnlineDetailTopping topping : listTopping) {
                    ToppingRes toppingRes = new ToppingRes();
                    toppingRes.setToppingID(topping.getTopping().getToppingID());
                    toppingRes.setToppingName(topping.getTopping().getToppingName());
                    toppingRes.setToppingPrice(topping.getTopping().getToppingPrice());
                    toppingRes.setQuantity(topping.getQuantity());
                    listToppingRes.add(toppingRes);
                }
            }

            ordDetailRes.setTopping(listToppingRes);
            listDetailRes.add(ordDetailRes);
        }

        orderRes.setOrd_details(listDetailRes);
        return orderRes;
    }
}
