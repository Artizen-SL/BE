package com.example.artizen.repository;

import com.example.artizen.entity.Artizen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtizenRepository extends JpaRepository<Artizen, String> {
    boolean existsById(String id);

    Slice<Artizen> findAllByCategoryContains(String genre, Pageable pageable);

    List<Artizen> findAllByCategoryContains(String keyword);

    Slice<Artizen> findAllByNameContainsOrderByCreatedAt(String keyword, Pageable pageable);

    Slice<Artizen> findAllByCategoryContainsOrderByCreatedAt(String keyword, Pageable pageable);
    
    Slice<Artizen> findAllByContentContainsOrderByCreatedAt(String keyword, Pageable pageable);

    Slice<Artizen> findAllByPlaceContainsOrderByCreatedAt(String keyword, Pageable pageable);

    List<Artizen> findAllByCategoryContains(String keyword);

    List<Artizen> findTop3ByOrderByCreatedAtDesc();

    List<Artizen> findTop4ByOrderByTotalHeartDesc();

    List<Artizen> findByPlace(String place);

    List<Artizen> findByPlaceAndCategoryContains(String place, String category);

}
