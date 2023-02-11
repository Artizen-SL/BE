package com.example.artizen.repository;

import com.example.artizen.entity.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findAllByOrderByCreatedAtDesc();

    Slice<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    Slice<Community> findByMember_MemberId(String memberId, Pageable pageable);
}
