package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CategoryReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CategoryRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CategoryService {
    private final WebClient webClient;


    public CategoryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/categories").build();
    }

   public List<CategoryRes> getAllCategories() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryRes>>(){})
                .block();

   }
   public CategoryRes getCategoryById(int id) {
        try{
            return webClient.get()
                    .uri("/{category_id}",id)
                    .retrieve()
                    .bodyToMono(CategoryRes.class)
                    .block();
        }catch (RuntimeException e){
            throw new RuntimeException("Category not found");
        }
   }

   public CategoryRes createCategory(CategoryReq req) {
    Boolean exists = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/exists")
                    .queryParam("name",req.getCategoryName()).build())
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();
    if(Boolean.TRUE.equals(exists)){
        throw new RuntimeException("Category already exists");
    }
        return webClient.post()
                .body(Mono.just(req),CategoryReq.class)
                .retrieve()
                .bodyToMono(CategoryRes.class)
                .block();
   }

   public String deleteCategory(int id){
        return webClient.delete()
                .uri("/{category_id}",id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
   }

   public CategoryRes updateCategory(int id,CategoryReq req){
        return webClient.put()
                .uri("/{category_id}",id)
                .body(Mono.just(req),CategoryReq.class)
                .retrieve()
                .bodyToMono(CategoryRes.class)
                .block();
   }
}
