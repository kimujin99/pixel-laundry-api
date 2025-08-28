package com.toy.pixel_laundry_api.repository;

import com.toy.pixel_laundry_api.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, String> {
}
