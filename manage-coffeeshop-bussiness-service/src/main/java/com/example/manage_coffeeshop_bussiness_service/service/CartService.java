package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CartRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.CartItemRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CartRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CartService {
    private final WebClient webClient;

//    public CartService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/carts").build();
//
//    }
    public CartService(WebClient.Builder webClientBuilder,@Value("${dataservice.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/carts").build();

    }

    public CartRes getOrCreateCart(int customerId) {
        return webClient.get()
                .uri("/{customerId}", customerId)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

//    public CartRes addToCart(int customerId, CartRequest req) {
//        return webClient.post()
//                .uri("/{customerId}/items", customerId)
//                .bodyValue(req)
//                .retrieve()
//                .bodyToMono(CartRes.class)
//                .block();
//    }
    public CartRes addToCart(int customerId, CartRequest req) {
        return webClient.post()
                .uri("/{customerId}/items", customerId)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public CartRes updateCart(int customerId, CartRequest req) {
        return webClient.put()
                .uri("/{customerId}", customerId)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public CartRes updateCartItem(int customerId, Long itemId, CartItemRequest req) {
        return webClient.put()
                .uri("/{customerId}/items/{itemId}", customerId, itemId)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }

    public CartRes deleteCartItem(int customerId, Long itemId) {
        return webClient.delete()
                .uri("/{customerId}/items/{itemId}", customerId, itemId)
                .retrieve()
                .bodyToMono(CartRes.class)
                .block();
    }
}
