package com.librework.modules.profile.infrastructure.adapter;

import com.librework.infrastructure.storage.CloudinaryService;
import com.librework.modules.profile.application.port.out.AvatarStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CloudinaryAvatarStorageAdapter implements AvatarStoragePort {

    private final CloudinaryService cloudinaryService;

    @Override
    public String upload(MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}