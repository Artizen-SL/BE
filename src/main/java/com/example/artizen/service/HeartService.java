package com.example.artizen.service;

import com.example.artizen.dto.response.MessageDto;
import com.example.artizen.entity.*;
import com.example.artizen.repository.ArtizenHeartRepository;
import com.example.artizen.repository.ArtizenRepository;
import com.example.artizen.repository.CommunityHeartRepository;
import com.example.artizen.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final ArtizenRepository artizenRepository;
    private final CommunityRepository communityRepository;
    private final ArtizenHeartRepository artizenHeartRepository;
    private final CommunityHeartRepository communityHeartRepository;


    @Transactional
    public ResponseEntity<?> checkArtizenHeart(Member member, String id) {

        Artizen artizen = artizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" 찾을 수 없는 공연입니다."));

        if (!artizenHeartRepository.existsByArtizenAndMember(artizen, member)){

            ArtizenHeart artizenHeart = new ArtizenHeart(artizen, member);
            artizenHeartRepository.save(artizenHeart);

            return ResponseEntity.status(ResponseCode.CHECK_HEART.getStatus()).body(new MessageDto(ResponseCode.CHECK_HEART.getCode(), ResponseCode.CHECK_HEART.getMsg()));
        }

        ArtizenHeart artizenHeart = artizenHeartRepository.findByArtizenAndMember(artizen, member);
        artizenHeartRepository.delete(artizenHeart);

        return ResponseEntity.status(ResponseCode.CANCEL_HEART.getStatus()).body(new MessageDto(ResponseCode.CANCEL_HEART.getCode(), ResponseCode.CANCEL_HEART.getMsg()));
    }


    @Transactional
    public ResponseEntity<?> checkCommunityHeart(Member member, Long id) {

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 게시글 입니다."));

        if (!communityHeartRepository.existsByCommunityAndMember(community, member)){

            CommunityHeart communityHeart = new CommunityHeart(community, member);
            communityHeartRepository.save(communityHeart);

            return ResponseEntity.status(ResponseCode.CHECK_HEART.getStatus()).body(new MessageDto(ResponseCode.CHECK_HEART.getCode(), ResponseCode.CHECK_HEART.getMsg()));
        }

        CommunityHeart communityHeart = communityHeartRepository.findByCommunityAndMember(community, member);
        communityHeartRepository.delete(communityHeart);

        return ResponseEntity.status(ResponseCode.CANCEL_HEART.getStatus()).body(new MessageDto(ResponseCode.CANCEL_HEART.getCode(), ResponseCode.CANCEL_HEART.getMsg()));
    }
}
