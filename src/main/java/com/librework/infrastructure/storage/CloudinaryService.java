package com.librework.infrastructure.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    //Multipart: File từ phía frontend gửi đến
    public String uploadImage(MultipartFile file)
    {
        if(file == null || file.isEmpty())
        {
            throw new AppException(ErrorCode.FILE_NOT_VALID);
        }

        try {
            // uploader().upload(): API để upload
            // Tạo params cho cloudinary
            Map<String, Object> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "librework",
                            "resource_type", "image"
                    )
            );
            // Trả về:
            // secure_url, public_id, format
            return result.get("secure_url").toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}