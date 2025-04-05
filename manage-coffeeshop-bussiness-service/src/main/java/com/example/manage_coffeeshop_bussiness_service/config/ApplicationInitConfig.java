package com.example.manage_coffeeshop_bussiness_service.config;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import com.example.manage_coffeeshop_bussiness_service.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(EmployeeService employeeService){
        return args -> {
            String account ="admin";
            if(employeeService.findEmployeeByAccount(account)==null){
                EmployeeReq emp = new EmployeeReq();
                emp.setEmpName("admin");
                emp.setEmpYearOfBirth(2000);
                emp.setEmpPhone("0123456789");
                emp.setEmpAccount("admin");
                emp.setEmpPassword("admin");
                emp.setEmpRole(Role.ADMIN);

                employeeService.createEmployee(emp);
                log.warn("admin created, change password");

            }else {
                log.warn("admin already exists");
            }
        };
    }
}
