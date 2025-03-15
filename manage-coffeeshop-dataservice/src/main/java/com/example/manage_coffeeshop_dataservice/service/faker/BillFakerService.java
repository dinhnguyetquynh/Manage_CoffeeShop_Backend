package com.example.manage_coffeeshop_dataservice.service.faker;

import com.example.manage_coffeeshop_dataservice.model.*;
import com.example.manage_coffeeshop_dataservice.repository.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BillFakerService {
    private final Faker faker;
    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final BillDetailRepository billDetailRepository;

    public BillFakerService(Faker faker, BillRepository billRepository, CustomerRepository customerRepository,
                            EmployeeRepository employeeRepository, ProductRepository productRepository,
                            BillDetailRepository billDetailRepository) {
        this.faker = faker;
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.billDetailRepository = billDetailRepository;
    }

    public void generateFakeBills(int count) {
        List<Customer> customers = new ArrayList<>(customerRepository.findAll());
        List<Employee> employees = new ArrayList<>(employeeRepository.findAll());
        List<Product> products = new ArrayList<>(productRepository.findAll());

        // Kiểm tra dữ liệu đầu vào
        if (customers.isEmpty() || employees.isEmpty() || products.isEmpty()) {
            throw new IllegalStateException("Không thể tạo hóa đơn. Dữ liệu khách hàng, nhân viên hoặc sản phẩm trống.");
        }

        for (int i = 0; i < count; i++) {
            // Chọn ngẫu nhiên Customer và Employee
            Customer customer = customers.get(faker.number().numberBetween(0, customers.size()));
            Employee employee = employees.get(faker.number().numberBetween(0, employees.size()));

            // Tạo Bill với các trường mới
            Bill bill = new Bill();
            bill.setCustomer(customer);
            bill.setEmployee(employee);

            // 1. Thêm billCreationDate (trong vòng 30 ngày)
            bill.setBillCreationDate(LocalDate.from(LocalDateTime.now().minusDays(faker.number().numberBetween(0,30))));

            // 2. Thêm paymentMethod (ngẫu nhiên)
            String[] paymentMethods = {"Cash", "Credit Card", "Momo", "ZaloPay"};
            bill.setPaymentMethod(faker.options().nextElement(paymentMethods));

            // Lưu Bill để có ID
            Bill savedBill = billRepository.save(bill);

            // Tạo BillDetail và tính tổng hóa đơn
            double total = 0;
            int detailCount = faker.number().numberBetween(1, 6);
            for (int j = 0; j < detailCount; j++) {
                Product product = products.get(faker.number().numberBetween(0, products.size()));

                BillProductKey id = new BillProductKey();
                id.setBillId(savedBill.getBillId());
                id.setProductId(product.getProductId());

                BillDetail detail = new BillDetail();
                detail.setId(id);
                detail.setBill(savedBill);
                detail.setProduct(product);
                detail.setProductQuantity(faker.number().numberBetween(1, 5));
                detail.setSubTotal(product.getProductPrice() * detail.getProductQuantity());

                total += detail.getSubTotal(); // Cộng dồn vào tổng hóa đơn

                billDetailRepository.save(detail);
            }

            // 3. Cập nhật tổng hóa đơn (billTotal)
            savedBill.setBillTotal(total);
            billRepository.save(savedBill); // Cập nhật lại Bill
        }
    }
}

//@Service
//public class BillFakerService {
//    private final Faker faker;
//    private final BillRepository billRepository;
//    private final CustomerRepository customerRepository;
//    private final EmployeeRepository employeeRepository;
//    private final ProductRepository productRepository;
//    private final BillDetailRepository billDetailRepository;
//
//    public BillFakerService(Faker faker, BillRepository billRepository, CustomerRepository customerRepository,
//                            EmployeeRepository employeeRepository, ProductRepository productRepository,
//                            BillDetailRepository billDetailRepository) {
//        this.faker = faker;
//        this.billRepository = billRepository;
//        this.customerRepository = customerRepository;
//        this.employeeRepository = employeeRepository;
//        this.productRepository = productRepository;
//        this.billDetailRepository = billDetailRepository;
//    }
//
//    public void generateFakeBills(int count) {
//        List<Customer> customers = new ArrayList<>(customerRepository.findAll());
//        List<Employee> employees = new ArrayList<>(employeeRepository.findAll());
//        List<Product> products = new ArrayList<>(productRepository.findAll());
//
//        String[] paymentMethods = {"Tiền mặt", "Chuyển khoản", "Momo", "ZaloPay"};
//
//        for (int i = 0; i < count; i++) {
//            // Chọn ngẫu nhiên khách hàng và nhân viên
//            Customer customer = customers.get(faker.number().numberBetween(0, customers.size()));
//            Employee employee = employees.get(faker.number().numberBetween(0, employees.size()));
//
//            Bill bill = new Bill();
//            bill.setCustomer(customer);
//            bill.setEmployee(employee);
//            bill.setBillCreationDate(LocalDate.from(LocalDateTime.now().minusDays(faker.number().numberBetween(0,30))));
//            bill.setPaymentMethod(faker.options().nextElement(paymentMethods));
//
//            Bill savedBill = billRepository.save(bill);
//
//            // Tạo chi tiết hóa đơn
//            double total = 0;
//            int detailCount = faker.number().numberBetween(1, 5);
//            for (int j = 0; j < detailCount; j++) {
//                Product product = products.get(faker.number().numberBetween(0, products.size()));
//
//                BillProductKey id = new BillProductKey();
//                id.setBillId(savedBill.getBillId());
//                id.setProductId(product.getProductId());
//
//                BillDetail detail = new BillDetail();
//                detail.setId(id);
//                detail.setBill(savedBill);
//                detail.setProduct(product);
//                detail.setProductQuantity(faker.number().numberBetween(1, 3));
//                detail.setSubTotal(product.getProductPrice() * detail.getProductQuantity());
//                total += detail.getSubTotal();
//
//                billDetailRepository.save(detail);
//            }
//
//            // Cập nhật tổng hóa đơn
//            savedBill.setBillTotal(total);
//            billRepository.save(savedBill);
//        }
//    }
//}

