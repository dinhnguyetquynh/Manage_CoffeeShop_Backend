package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthenticationService {
    private final WebClient webClient;


    public AuthenticationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/employee").build();
    }



    public boolean authenticate(AuthenticationRequest req) {
        EmployeeRes emp = findEmployeeById(req);
        if(emp == null) {
            throw new RuntimeException("Invalid username or password");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(req.getPassword(), emp.getEmpPassword());
    }

    public EmployeeRes findEmployeeById(AuthenticationRequest req) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("name",req.getUsername()).build())
                .retrieve()
                .bodyToMono(EmployeeRes.class)
                .block();
    }



}
