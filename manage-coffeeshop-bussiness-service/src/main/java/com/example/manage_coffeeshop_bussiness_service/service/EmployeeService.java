package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public List<EmployeeRes> getAllEmployees() {
        return webClient.get()
                .uri("/list")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EmployeeRes>>(){})
                .block();
    }

}
