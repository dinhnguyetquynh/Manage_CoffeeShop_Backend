package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ToppingRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ToppingRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ToppingService {
    private final WebClient webClient;

//    public ToppingService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl("http://localhost:8080/myapp/api/toppings")
//                .build();
//    }
public ToppingService(WebClient.Builder webClientBuilder,
                       @Value("${dataservice.base-url}") String baseUrl) {
    this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/toppings").build();
}

    public ToppingRes createTopping(ToppingRequest req) {
        return webClient.post()
                .body(Mono.just(req), ToppingRequest.class)
                .retrieve()
                .bodyToMono(ToppingRes.class)
                .block();
    }

    public List<ToppingRes> getAllToppings() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ToppingRes>>() {})
                .block();
    }

    public ToppingRes findToppingById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ToppingRes.class)
                .block();
    }

    public ToppingRes updateTopping(Long id, ToppingRequest req) {
        return webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(req), ToppingRequest.class)
                .retrieve()
                .bodyToMono(ToppingRes.class)
                .block();
    }

    public String deleteTopping(Long id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
