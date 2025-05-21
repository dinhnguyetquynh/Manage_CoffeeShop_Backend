package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.respone.CategoryRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.TransactionHistoryRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class TransactionHistoryService {
    private final WebClient webClient;

//    public TransactionHistoryService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/history").build();
//
//    }
public TransactionHistoryService(WebClient.Builder webClientBuilder,
                       @Value("${dataservice.base-url}") String baseUrl) {
    this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/history").build();
}

    public List<TransactionHistoryRes> getAllTrHistory(int customerId) {
        return webClient.get()
                .uri("/{customerId}", customerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TransactionHistoryRes>>() {})
                .block();
    }
}
