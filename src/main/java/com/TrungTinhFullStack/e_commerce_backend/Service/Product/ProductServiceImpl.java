package com.TrungTinhFullStack.e_commerce_backend.Service.Product;

import com.TrungTinhFullStack.e_commerce_backend.Exception.ProductNotFoundException;
import com.TrungTinhFullStack.e_commerce_backend.Model.Category;
import com.TrungTinhFullStack.e_commerce_backend.Model.Product;
import com.TrungTinhFullStack.e_commerce_backend.Repository.CategoryRepository;
import com.TrungTinhFullStack.e_commerce_backend.Repository.ProductRepository;
import com.TrungTinhFullStack.e_commerce_backend.Request.AddProductRequest;
import com.TrungTinhFullStack.e_commerce_backend.Request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository caterogyRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(caterogyRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
            Category category1 = new Category(request.getCategory().getName());
            return caterogyRepository.save(category1);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> new ProductNotFoundException("Product not found !"));
    }

    @Override
    public Product updateProduct(Long id, UpdateProductRequest request) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = caterogyRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
