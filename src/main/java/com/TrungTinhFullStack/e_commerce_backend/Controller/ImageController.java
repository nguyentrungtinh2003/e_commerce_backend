package com.TrungTinhFullStack.e_commerce_backend.Controller;

import com.TrungTinhFullStack.e_commerce_backend.Dto.ImageDto;
import com.TrungTinhFullStack.e_commerce_backend.Exception.ImageNotFoundException;
import com.TrungTinhFullStack.e_commerce_backend.Model.Image;
import com.TrungTinhFullStack.e_commerce_backend.Response.ApiResponse;
import com.TrungTinhFullStack.e_commerce_backend.Service.Image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> addImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.addImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload success !",imageDtos));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed !",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName() + "\"").body(resource);

    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Update success !", null));
            }
        } catch(ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Image not found !",null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed !",HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImage(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success !", null));
            }
        } catch(ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Image not found !",null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed !",HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
