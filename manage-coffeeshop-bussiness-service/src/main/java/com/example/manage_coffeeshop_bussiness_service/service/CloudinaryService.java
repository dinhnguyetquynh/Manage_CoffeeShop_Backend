package com.example.manage_coffeeshop_bussiness_service.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data.get("url").toString();
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }
}
