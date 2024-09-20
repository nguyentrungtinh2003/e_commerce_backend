package com.TrungTinhFullStack.e_commerce_backend.Service.Category;

import com.TrungTinhFullStack.e_commerce_backend.Model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);

}
