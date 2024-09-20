package com.TrungTinhFullStack.e_commerce_backend.Repository;

import com.TrungTinhFullStack.e_commerce_backend.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
