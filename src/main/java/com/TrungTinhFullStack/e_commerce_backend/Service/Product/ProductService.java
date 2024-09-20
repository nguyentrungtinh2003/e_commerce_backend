package com.TrungTinhFullStack.e_commerce_backend.Service.Product;

import com.TrungTinhFullStack.e_commerce_backend.Model.Product;
import com.TrungTinhFullStack.e_commerce_backend.Request.AddProductRequest;
import com.TrungTinhFullStack.e_commerce_backend.Request.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest request);
    List<Product> getAllProduct();
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(Long id, UpdateProductRequest request);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
