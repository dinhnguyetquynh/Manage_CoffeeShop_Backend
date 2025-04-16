package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.OrderRes;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {
    private final WebClient webClient;
    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/order").build();
    }

    public String createOrder(OrderRequest orderRequest) {
        String messageForCreateOrder = webClient.post()
                .body(Mono.just(orderRequest),OrderRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return messageForCreateOrder;
    }


    public OrderRes findOrderById(int id){
        return webClient.get()
                .uri("/{id}",id)
                .retrieve()
                .bodyToMono(OrderRes.class)
                .block();

    }

    public List<OrderRes> findOrderByDate(String date){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/date")
                        .queryParam("date",date)
                        .build())
                .retrieve()
                .bodyToFlux(OrderRes.class)
                .collectList()
                .block();

    }
}
