package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.CustomerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {


    private final JavaMailSender mailSender;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void sendOtp(CustomerRequest customer) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // mã 6 chữ số
        String email = customer.getEmail();

        // Gửi OTP qua email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);

        // Lưu OTP và thông tin khách hàng vào Redis
        Map<String, Object> data = new HashMap<>();
        data.put("otp", otp);
        data.put("customer", customer);

        redisTemplate.opsForValue().set("OTP:" + email, data, Duration.ofMinutes(5));
    }

    public boolean verifyOtp(String email, String otp) {
        Map<String, Object> data = (Map<String, Object>) redisTemplate.opsForValue().get("OTP:" + email);
        return data != null && otp.equals(data.get("otp"));
    }

    public CustomerRequest getCustomer(String email) {
        Map<String, Object> data = (Map<String, Object>) redisTemplate.opsForValue().get("OTP:" + email);

        return data != null ? objectMapper.convertValue(data.get("customer"), CustomerRequest.class) : null;

    }

    public void clearOtp(String email) {
        redisTemplate.delete("OTP:" + email);
    }
}
