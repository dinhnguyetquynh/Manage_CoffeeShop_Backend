package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {
    private final WebClient webClient;


    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/customers").build();
    }

    public String createCustomer(CustomerReq customerReq) {
        return webClient.post()
                .body(Mono.just(customerReq),CustomerReq.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public CustomerRes findCustomerByPhone(String phone){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/phone")
                        .queryParam("phone",phone)
                        .build())
                .retrieve()
                .bodyToMono(CustomerRes.class)
                .block();

    }

    public CustomerRes findCustomerById(int id){
        return webClient.get()
                .uri("/{id}",id)
                .retrieve()
                .bodyToMono(CustomerRes.class)
                .block();
    }

    public String deleteCustomer(int id){
        return webClient.delete()
                .uri("/{id}",id)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }

    public List<CustomerRes> getAllCustomer(){
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CustomerRes>>(){})
                .block();

    }
}
