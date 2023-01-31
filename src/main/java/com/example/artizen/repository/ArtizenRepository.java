package com.example.artizen.repository;

import com.example.artizen.entity.Artizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtizenRepository extends JpaRepository<Artizen, String> {
    boolean existsById(String id);

    List<Artizen> findAllByCategoryContains(String genre);

}
