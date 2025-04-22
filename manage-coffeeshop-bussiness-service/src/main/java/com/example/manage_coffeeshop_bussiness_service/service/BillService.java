package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.BillReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.BillRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BillService {
    private final WebClient webClient;

    public BillService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/bills").build();
    }

    public List<BillRes> getAllBills() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BillRes>>() {})
                .block();
    }

    public BillRes getBillById(int id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(BillRes.class)
                .block();
    }

    public BillRes createBill(BillReq req) {
        return webClient.post()
                .body(Mono.just(req), BillReq.class)
                .retrieve()
                .bodyToMono(BillRes.class)
                .block();
    }

    public BillRes updateBill(int id, BillReq req) {
        return webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(req), BillReq.class)
                .retrieve()
                .bodyToMono(BillRes.class)
                .block();
    }

    public String deleteBill(int id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}