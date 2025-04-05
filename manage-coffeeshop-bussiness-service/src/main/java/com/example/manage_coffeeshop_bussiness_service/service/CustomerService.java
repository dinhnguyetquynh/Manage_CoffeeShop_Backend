package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerReq;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final WebClient webClient;


    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/customers").build();
    }

    public String createCustomer(CustomerReq customerReq) {
        return webClient.post()
                .body(Mono.just(customerReq),CustomerReq.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
