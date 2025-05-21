package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderOnlineRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.OrderOnlineRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class OrderOnlineService {
    private final WebClient webClient;

//
//    public OrderOnlineService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/sepay").build();
//    }
    public OrderOnlineService(WebClient.Builder webClientBuilder,@Value("${dataservice.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/sepay").build();
    }

    public OrderOnlineRes createOrderOnline(OrderOnlineRequest req) {
        return webClient.post()
                .uri("/create")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(OrderOnlineRes.class)
                .block();
    }
    public OrderOnlineRes checkStatusOrder(int id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(OrderOnlineRes.class)
                .block();
    }



}
