package com.example.artizen.repository;

import com.example.artizen.entity.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Slice<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    Slice<Community> findByMember_Id(String memberId, Pageable pageable);
}
