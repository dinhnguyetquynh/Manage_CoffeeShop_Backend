package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.DiscountCodeRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.DiscountCodeRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DiscountService {
    private final WebClient webClient;

    public DiscountService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080/myapp/api/discounts").build();
    }

    public List<DiscountCodeRes> getAllDiscountCodes() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DiscountCodeRes>>() {})
                .block();
    }

    public DiscountCodeRes findByDiscountCode(String code) {
        return webClient.get().uri("/{code}", code)
                .retrieve().bodyToMono(DiscountCodeRes.class)
                .block();
    }

    public DiscountCodeRes createDiscount(DiscountCodeRequest req) {
        return webClient.post()
                .body(Mono.just(req), DiscountCodeRequest.class)
                .retrieve().bodyToMono(DiscountCodeRes.class)
                .block();
    }

    public DiscountCodeRes updateDiscount(Integer id, DiscountCodeRequest req) {
        return webClient.put().uri("/{id}", id)
                .body(Mono.just(req), DiscountCodeRequest.class)
                .retrieve().bodyToMono(DiscountCodeRes.class)
                .block();
    }

    public String deleteDiscount(Integer id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}