package com.example.artizen.repository;

import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, String> {
    Boolean existsByImageUrlAndArtizen(String imageUrl, Artizen artizen);

}
