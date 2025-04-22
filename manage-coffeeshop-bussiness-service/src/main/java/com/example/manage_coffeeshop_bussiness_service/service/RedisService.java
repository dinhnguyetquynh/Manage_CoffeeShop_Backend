package com.example.manage_coffeeshop_bussiness_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    public void saveRefreshToken(String token, long expirationInSeconds) {
        redisTemplate.opsForValue().set(token, "valid", expirationInSeconds, TimeUnit.SECONDS);
    }

    public boolean exists(String token) {
        return redisTemplate.hasKey(token);
    }

    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }
}
