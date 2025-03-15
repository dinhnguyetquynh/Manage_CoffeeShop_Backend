package com.example.manage_coffeeshop_dataservice.service.faker;

import com.example.manage_coffeeshop_dataservice.model.Employee;
import com.example.manage_coffeeshop_dataservice.repository.EmployeeRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class EmployeeFakerService {
    private final Faker faker;
    private final EmployeeRepository employeeRepository;

    public EmployeeFakerService(Faker faker, EmployeeRepository employeeRepository) {
        this.faker = faker;
        this.employeeRepository = employeeRepository;
    }

    public void generateFakeEmployees(int count) {
        for (int i = 0; i < count; i++) {
            Employee employee = new Employee();
            employee.setEmpName(faker.name().fullName());
            employee.setEmpYearOfBirth(faker.number().numberBetween(1980, 2000));
            employee.setEmpPhone(faker.phoneNumber().cellPhone());
            employee.setEmpRole(faker.number().numberBetween(0, 3)); // 0: Staff, 1: Manager, 2: Admin
            employee.setEmpAccount(faker.internet().username());
            employee.setEmpPassword(faker.internet().password());
            employeeRepository.save(employee);
        }
    }
}

//@Service
//public class EmployeeFakerService {
//    private final Faker faker;
//    private final EmployeeRepository employeeRepository;
//
//    public EmployeeFakerService(Faker faker, EmployeeRepository employeeRepository) {
//        this.faker = faker;
//        this.employeeRepository = employeeRepository;
//    }
//
//    public void generateFakeEmployees(int count) {
//        String[] firstNames = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng"};
//        String[] lastNames = {"Văn An", "Thị Hương", "Công Minh", "Duy Khánh", "Minh Tuấn"};
//        String[] roles = {"Phục vụ", "Thu ngân", "Quản lý", "Pha chế"};
//
//        for (int i = 0; i < count; i++) {
//            Employee employee = new Employee();
//            String fullName = faker.options().nextElement(firstNames) + " " + faker.options().nextElement(lastNames);
//            employee.setEmpName(fullName);
//            employee.setEmpYearOfBirth(faker.number().numberBetween(1990, 2000));
//            employee.setEmpPhone("09" + faker.number().digits(8));;
//            employee.setEmpRole(faker.number().numberBetween(0, 3)); // 0: Staff, 1: Manager, 2: Admin
//            employee.setEmpAccount(faker.internet().username());
//            employee.setEmpPassword(faker.internet().password());
//            employeeRepository.save(employee);
//        }
//    }
//}

