//package com.example.manage_coffeeshop_dataservice;
//
//import com.example.manage_coffeeshop_dataservice.service.faker.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//
//@SpringBootApplication
//public class DataFakerRunner {
//
//    private static CategoryFakerService categoryFakerService;
//    private static ProductFakerService productFakerService;
//    private static CustomerFakerService customerFakerService;
//    private static EmployeeFakerService employeeFakerService;
//    private static BillFakerService billFakerService;
//
//    @Autowired
//    public DataFakerRunner(
//            CategoryFakerService categoryFakerService,
//            ProductFakerService productFakerService,
//            CustomerFakerService customerFakerService,
//            EmployeeFakerService employeeFakerService,
//            BillFakerService billFakerService
//    ) {
//        DataFakerRunner.categoryFakerService = categoryFakerService;
//        DataFakerRunner.productFakerService = productFakerService;
//        DataFakerRunner.customerFakerService = customerFakerService;
//        DataFakerRunner.employeeFakerService = employeeFakerService;
//        DataFakerRunner.billFakerService = billFakerService;
//    }
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(DataFakerRunner.class, args);
//        generateFakeData(); // Gọi hàm tạo dữ liệu giả
//    }
//
//    private static void generateFakeData() {
//        // Tạo dữ liệu giả
//        categoryFakerService.generateFakeCategories(5);
//        productFakerService.generateFakeProducts(10);
//        customerFakerService.generateFakeCustomers(20);
//        employeeFakerService.generateFakeEmployees(10);
//        billFakerService.generateFakeBills(50);
//
//        System.out.println("Tạo thành công Fake data !");
//    }
//}