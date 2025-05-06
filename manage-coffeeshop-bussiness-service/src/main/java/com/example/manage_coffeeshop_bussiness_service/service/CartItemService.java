package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartItemRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CartItemService {
    private final WebClient webClient;

    public CartItemService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080/myapp/api/cart-items")
                .build();
    }

    public CartItemRes createCartItem(CartItemRequest req) {
        return webClient.post()
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CartItemRes.class)
                .block();
    }

    public List<CartItemRes> getAllCartItems() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CartItemRes>>() {})
                .block();
    }

    public CartItemRes findCartItemById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(CartItemRes.class)
                .block();
    }

    public CartItemRes updateCartItem(Long id, CartItemRequest req) {
        return webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(req), CartItemRequest.class)
                .retrieve()
                .bodyToMono(CartItemRes.class)
                .block();
    }

    public String deleteCartItem(Long id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public CartItemRes addToppingsToCartItem(Long cartItemId, List<Long> toppingIds) {
        return webClient.put()
                .uri("/{id}/toppings", cartItemId)
                .bodyValue(toppingIds)
                .retrieve()
                .bodyToMono(CartItemRes.class)
                .block();
    }
}