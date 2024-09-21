package com.TrungTinhFullStack.e_commerce_backend.Controller;

import com.TrungTinhFullStack.e_commerce_backend.Exception.ProductNotFoundException;
import com.TrungTinhFullStack.e_commerce_backend.Model.Product;
import com.TrungTinhFullStack.e_commerce_backend.Request.AddProductRequest;
import com.TrungTinhFullStack.e_commerce_backend.Request.UpdateProductRequest;
import com.TrungTinhFullStack.e_commerce_backend.Response.ApiResponse;
import com.TrungTinhFullStack.e_commerce_backend.Service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(new ApiResponse("Success !", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success !", product));
        } catch (ProductNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add success !",product1));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,@RequestBody UpdateProductRequest product) {
        try {
            Product product1 = productService.updateProduct(id,product);
            return ResponseEntity.ok(new ApiResponse("Update success !",product1));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Delete success !",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand/{brandName}/name/{productName}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName,productName);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found !",productName));
            }
            return ResponseEntity.ok(new ApiResponse("Success !",products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{category}/name/{brand}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found !",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success !",products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> findProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found !",name));
            }
            return ResponseEntity.ok(new ApiResponse("Success !",products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> findProductByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found !",brand));
            }
            return ResponseEntity.ok(new ApiResponse("Success !",products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found !",category));
            }
            return ResponseEntity.ok(new ApiResponse("Success !",products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/count/brand/{brand}/name/{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand,@PathVariable String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("product count ; !",productCount));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
