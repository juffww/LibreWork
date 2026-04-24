package com.librework.modules.profile.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarStoragePort {
    String upload(MultipartFile file);
}