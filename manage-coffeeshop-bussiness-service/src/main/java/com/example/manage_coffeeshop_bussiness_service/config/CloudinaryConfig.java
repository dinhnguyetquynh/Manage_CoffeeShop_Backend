package com.example.manage_coffeeshop_bussiness_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinaryConfig2() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "dh6qeem5m");
        config.put("api_key", "557743328436612");
        config.put("api_secret", "oqLjHGI6BUHFaR5yG4o6h_73nao");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
