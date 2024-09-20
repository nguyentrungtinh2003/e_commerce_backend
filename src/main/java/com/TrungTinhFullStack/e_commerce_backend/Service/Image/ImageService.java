package com.TrungTinhFullStack.e_commerce_backend.Service.Image;

import com.TrungTinhFullStack.e_commerce_backend.Dto.ImageDto;
import com.TrungTinhFullStack.e_commerce_backend.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> addImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
