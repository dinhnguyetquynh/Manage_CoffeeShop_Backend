package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.BillDetailReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.BillDetailRes;
import com.example.manage_coffeeshop_bussiness_service.exception.AppException;
import com.example.manage_coffeeshop_bussiness_service.exception.ErrorCode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class BillDetailService {
    private final WebClient webClient;

    public BillDetailService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/bill-details").build();
    }

    public List<BillDetailRes> getAllBillDetails() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BillDetailRes>>() {})
                .block();
    }

        public List<BillDetailRes> getDetailsByBillId(int billId) {
        return webClient.get()
                .uri("/bill/{billId}", billId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BillDetailRes>>() {})
                .block();
    }

    public BillDetailRes createDetail(BillDetailReq req) {
        try {
            return webClient.post()
                    .bodyValue(req)
                    .retrieve()
                    .bodyToMono(BillDetailRes.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
                throw new AppException(ErrorCode.BILL_DETAIL_EXISTS);
            }
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }
    }

    public BillDetailRes updateDetail(BillDetailReq req) {
        return webClient.put()
                .body(Mono.just(req), BillDetailReq.class)
                .retrieve()
                .bodyToMono(BillDetailRes.class)
                .block();
    }

    public void deleteDetail(int billId, int productId) {
        webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("billId", billId)
                        .queryParam("productId", productId)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}