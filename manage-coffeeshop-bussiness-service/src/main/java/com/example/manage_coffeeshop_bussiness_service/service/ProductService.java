package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.ProductReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.ProductRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {
    private final WebClient webClient;
//    public ProductService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/products").build();
//    }
public ProductService(WebClient.Builder webClientBuilder,
                       @Value("${dataservice.base-url}") String baseUrl) {
    this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/products").build();
}
    public String createProduct(ProductReq productReq) {
        return webClient.post()
                .body(Mono.just(productReq),ProductReq.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public List<ProductRes> getAllProduct(){
        List<ProductRes> lists= webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductRes>>(){})
                .block();
        for(ProductRes pro:lists){
            System.out.println("Product is:"+pro.toString());
        }
        return lists;
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

    public List<ProductRes> getProductsByCategory(int categoryId){
        List<ProductRes> lists= webClient.get()
                .uri("/category/{categoryId}",categoryId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductRes>>(){})
                .block();

        return lists;
    }

    public ProductRes updateProduct(int id,ProductReq req){
        return webClient.put()
                .uri("/{id}",id)
                .body(Mono.just(req),ProductReq.class)
                .retrieve()
                .bodyToMono(ProductRes.class)
                .block();

    }

    public String deleteProductById(int id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
