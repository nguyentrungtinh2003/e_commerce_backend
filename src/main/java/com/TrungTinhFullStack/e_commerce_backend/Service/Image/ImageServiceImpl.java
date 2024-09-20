package com.TrungTinhFullStack.e_commerce_backend.Service.Image;

import com.TrungTinhFullStack.e_commerce_backend.Dto.ImageDto;
import com.TrungTinhFullStack.e_commerce_backend.Exception.ImageNotFoundException;
import com.TrungTinhFullStack.e_commerce_backend.Model.Image;
import com.TrungTinhFullStack.e_commerce_backend.Model.Product;
import com.TrungTinhFullStack.e_commerce_backend.Repository.ImageRepository;
import com.TrungTinhFullStack.e_commerce_backend.Service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found !"));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ImageNotFoundException("Image not found !");
        });
    }

    @Override
    public List<ImageDto> addImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> saveImageDto = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/images/image/download/";

                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image image1 = imageRepository.save(image);

                image1.setDownloadUrl(buildDownloadUrl + image1.getId());

                imageRepository.save(image1);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(image1.getId());
                imageDto.setImageName(image1.getFileName());
                imageDto.setDownloadUrl(image1.getDownloadUrl());
                saveImageDto.add(imageDto);

            } catch (SerialException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return saveImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        try {
            Image image = getImageById(imageId);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
