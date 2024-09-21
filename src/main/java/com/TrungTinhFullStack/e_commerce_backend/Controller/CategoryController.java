package com.TrungTinhFullStack.e_commerce_backend.Controller;

import com.TrungTinhFullStack.e_commerce_backend.Exception.AlreadyExistsException;
import com.TrungTinhFullStack.e_commerce_backend.Exception.CategoryNotFoundException;
import com.TrungTinhFullStack.e_commerce_backend.Model.Category;
import com.TrungTinhFullStack.e_commerce_backend.Response.ApiResponse;
import com.TrungTinhFullStack.e_commerce_backend.Service.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategory() {
        try{
            List<Category> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found !",categories));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error : ",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category category1 = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Add success !",category1));
        }catch(AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/id//{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
       try{
           Category category = categoryService.getCategoryById(id);
           return ResponseEntity.ok(new ApiResponse("Found !",category));
       }catch(CategoryNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
       }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try{
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found !",category));
        }catch(CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Delete success !",null));
        }catch(CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category) {
        try{
            Category category1 = categoryService.updateCategory(id,category);
            return ResponseEntity.ok(new ApiResponse("Update success !",category1));
        }catch(CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
