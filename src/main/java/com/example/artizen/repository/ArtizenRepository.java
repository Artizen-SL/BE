package com.example.artizen.repository;

import com.example.artizen.entity.Artizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtizenRepository extends JpaRepository<Artizen, String> {
    boolean existsById(String id);

    List<Artizen> findAllByCategoryContains(String genre);

    Page<Artizen> findAllByNameContainsOrderByCreatedAt(String keyword, Pageable pageable);

    Page<Artizen> findAllByCategoryContainsOrderByCreatedAt(String keyword, Pageable pageable);

    Page<Artizen> findAllByContentContainsOrderByCreatedAt(String keyword, Pageable pageable);

    Page<Artizen> findAllByPlaceContainsOrderByCreatedAt(String keyword, Pageable pageable);

    List<Artizen> findAllByNameContains(String keyword);

}
