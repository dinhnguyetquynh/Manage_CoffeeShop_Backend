    package com.example.manage_coffeeshop_dataservice.dto.respone;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BillDetailRes {
        private int billId;
        private int productId;
        private int productQuantity;
        private double subTotal;
    }
