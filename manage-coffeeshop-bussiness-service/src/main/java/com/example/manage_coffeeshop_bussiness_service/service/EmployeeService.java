package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
    private final WebClient webClient;


    public EmployeeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/employee").build();
    }

    public EmployeeRes createEmployee(EmployeeReq employeeReq) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        employeeReq.setEmpPassword(passwordEncoder.encode(employeeReq.getEmpPassword()));

        return webClient.post()
                .body(Mono.just(employeeReq),EmployeeReq.class)
                .retrieve()
                .bodyToMono(EmployeeRes.class)
                .block();
    }

}
