package com.example.artizen.repository;

import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.ArtizenHeart;
import com.example.artizen.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtizenHeartRepository extends JpaRepository<ArtizenHeart, Long> {
    Slice<ArtizenHeart> findHeartsByMember_Id(String memberId, Pageable pageable);

    Boolean existsByArtizenAndMember(Artizen artizen, Member member);

    ArtizenHeart findByArtizenAndMember(Artizen artizen, Member member);

    List<ArtizenHeart> findAllByArtizenId(String artizenId);
}
