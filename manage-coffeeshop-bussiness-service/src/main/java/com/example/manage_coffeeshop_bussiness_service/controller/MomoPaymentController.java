package com.example.manage_coffeeshop_bussiness_service.controller;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/business/payment")
public class MomoPaymentController {

    @Value("${momo.partnerCode}")
    private String partnerCode;
    @Value("${momo.accessKey}")
    private String accessKey;
    @Value("${momo.secretKey}")
    private String secretKey;
    @Value("${momo.endpoint}")
    private String endpoint;
    @Value("${momo.redirectUrl}")
    private String redirectUrl;
    @Value("${momo.ipnUrl}")
    private String ipnUrl;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam String amount) throws Exception {
        String orderId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toán đơn hàng demo";
        String requestType = "captureWallet";
        String extraData = ""; // Nếu có

        // Tạo chuỗi raw data để ký
        String rawSignature = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature = hmacSHA256(rawSignature, secretKey);

        // Dữ liệu gửi đến MoMo
        Map<String, Object> body = new HashMap<>();
        body.put("partnerCode", partnerCode);
        body.put("accessKey", accessKey);
        body.put("requestId", requestId);
        body.put("amount", amount);
        body.put("orderId", orderId);
        body.put("orderInfo", orderInfo);
        body.put("redirectUrl", redirectUrl);
        body.put("ipnUrl", ipnUrl);
        body.put("extraData", extraData);
        body.put("requestType", requestType);
        body.put("signature", signature);
        body.put("lang", "vi");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);

        return ResponseEntity.ok(response.getBody()); // Trả về payUrl, deeplink, qrCodeUrl
    }

    // MoMo gửi callback về đây nếu thanh toán thành công (sandbox vẫn gọi thử)
    @PostMapping("/ipn")
    public ResponseEntity<String> handleIpn(@RequestBody Map<String, Object> data) {
        System.out.println("IPN MoMo: " + data);
        return ResponseEntity.ok("Received");
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok("Thanh toán thành công (redirect): " + params);
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmac.init(secretKeySpec);
        byte[] result = hmac.doFinal(data.getBytes());
        return Hex.encodeHexString(result);
    }
}
