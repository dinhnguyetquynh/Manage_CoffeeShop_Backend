package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class    CustomerService {
    private final WebClient webClient;


    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/customers").build();
    }

    public CustomerRes createCustomer(CustomerRequest customerReq) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        customerReq.setPasswordCus(passwordEncoder.encode(customerReq.getPasswordCus()));
        return webClient.post()
                .body(Mono.just(customerReq), CustomerRequest.class)
                .retrieve()
                .bodyToMono(CustomerRes.class) // Parse kết quả thành CustomerRes
                .block(); // Lấy kết quả đồng bộ
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

    public CustomerRes updateCustomer(int id, CustomerRequest request) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(id))
                .body(Mono.just(request), CustomerRequest.class)
                .retrieve()
                .bodyToMono(CustomerRes.class)
                .block();
    }
    public String getRank(int customerId){
        return webClient.get()
                .uri("/rank/{customerId}",customerId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
