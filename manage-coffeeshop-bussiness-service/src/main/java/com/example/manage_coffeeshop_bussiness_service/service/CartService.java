package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CartService {
    private final WebClient webClient;

    public CartService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/myapp/api/carts")
                .build();
    }

    public CartRes createCart(CartRequest cartRequest) {
        return webClient.post()
                .body(Mono.just(cartRequest), CartRequest.class)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public List<CartRes> getAllCarts() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CartRes>>(){})
                .block();
    }

    public CartRes findCartById(Integer id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public CartRes updateCart(Integer id, CartRequest req) {
        return webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(req), CartRequest.class)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public String deleteCart(Integer id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
