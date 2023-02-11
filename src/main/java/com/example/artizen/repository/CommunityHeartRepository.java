package com.example.artizen.repository;

import com.example.artizen.entity.Community;
import com.example.artizen.entity.CommunityHeart;
import com.example.artizen.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityHeartRepository extends JpaRepository<CommunityHeart, Long> {

    Boolean existsByCommunityAndMember(Community community, Member member);

    CommunityHeart findByCommunityAndMember(Community community, Member member);
}
