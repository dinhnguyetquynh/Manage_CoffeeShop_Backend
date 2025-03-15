package com.example.manage_coffeeshop_dataservice.service.faker;

import com.example.manage_coffeeshop_dataservice.model.Customer;
import com.example.manage_coffeeshop_dataservice.repository.CustomerRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;


@Service
public class CustomerFakerService {
    private final Faker faker;
    private final CustomerRepository customerRepository;

    public CustomerFakerService(Faker faker, CustomerRepository customerRepository) {
        this.faker = faker;
        this.customerRepository = customerRepository;
    }

    public void generateFakeCustomers(int count) {
        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            customer.setCustomerName(faker.name().fullName());
            customer.setCustomerPhone(faker.phoneNumber().cellPhone());
            customerRepository.save(customer);
        }
    }
}

//@Service
//public class CustomerFakerService {
//    private final Faker faker;
//    private final CustomerRepository customerRepository;
//
//    public CustomerFakerService(Faker faker, CustomerRepository customerRepository) {
//        this.faker = faker;
//        this.customerRepository = customerRepository;
//    }
//
//    public void generateFakeCustomers(int count) {
//        String[] firstNames = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng"};
//        String[] lastNames = {"Văn An", "Thị Bình", "Công Chính", "Duy Đạt", "Minh Khôi"};
//
//        for (int i = 0; i < count; i++) {
//            Customer customer = new Customer();
//            String fullName = faker.options().nextElement(firstNames) + " " + faker.options().nextElement(lastNames);
//            customer.setCustomerName(fullName);
//            customer.setCustomerPhone("09" + faker.number().digits(8)); // Số điện thoại Việt Nam
//            customerRepository.save(customer);
//        }
//    }
//}
