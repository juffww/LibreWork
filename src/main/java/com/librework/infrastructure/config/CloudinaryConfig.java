package com.librework.infrastructure.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

// Đây là bean cloudinary cấu hình các thông số quan trọng để kết nối cloudinary
// Tạo config cấu hình kết nối 1 lần và sử dụng lại
@Configuration
public class CloudinaryConfig {
    @Value("${Cloudinary.cloud-name}")
    private String cloudName;

    @Value("${Cloudinary.api-key}")
    private String apiKey;

    @Value("${Cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "apiSecret", apiSecret
        ));
    }
}