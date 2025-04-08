package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ProductReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ProductRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {
    private final WebClient webClient;
    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/products").build();
    }

    public String createProduct(ProductReq productReq) {
        return webClient.post()
                .body(Mono.just(productReq),ProductReq.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public List<ProductRes> getAllProduct(){
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductRes>>(){})
                .block();
    }

    public ProductRes findProductById(int id){
        try {
            return webClient.get()
                    .uri("/{id}",id)
                    .retrieve()
                    .bodyToMono(ProductRes.class)
                    .block();
        }catch(RuntimeException ex){
            throw new RuntimeException("Product not found");

        }

    }
}
