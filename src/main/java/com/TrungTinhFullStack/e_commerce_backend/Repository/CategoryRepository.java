package com.TrungTinhFullStack.e_commerce_backend.Repository;

import com.TrungTinhFullStack.e_commerce_backend.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
