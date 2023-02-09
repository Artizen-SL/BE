package com.example.artizen.repository;

import com.example.artizen.entity.Heart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Slice<Heart> findHeartsByMember_Id(String memberId, Pageable pageable);

}
