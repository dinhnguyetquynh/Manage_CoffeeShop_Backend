package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeReq;
import com.example.manage_coffeeshop_bussiness_service.dto.request.EmployeeUpdateReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EmployeeService {
    private final WebClient webClient;


    public EmployeeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/employee").build();
    }

    public EmployeeRes findEmployeeByAccount(String account){

                return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("account",account)
                                .build())
                        .retrieve()
                        .bodyToMono(EmployeeRes.class)
                        .block();


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

    public EmployeeRes updateEmployee(int id,EmployeeUpdateReq req) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        req.setEmpPassword(passwordEncoder.encode(req.getEmpPassword()));
        return webClient.put()
                .uri("/{id}",id)
                .body(Mono.just(req),EmployeeUpdateReq.class)
                .retrieve()
                .bodyToMono(EmployeeRes.class)
                .block();
    }



}
