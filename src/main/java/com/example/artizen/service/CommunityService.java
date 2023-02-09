package com.example.artizen.service;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.entity.Community;
import com.example.artizen.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    @Value("${cloud.aws.s3.dir}")
    private String dir;
    private final CommunityRepository communityRepository;
    private final S3UploadService s3UploadService;


    @Transactional
    public ResponseEntity<?> createCommunity(CommunityRequestDto requestDto) throws IOException {

        Community community = new Community(requestDto, s3UploadService, dir);
        communityRepository.save(community);

        return new ResponseEntity<>(community, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getCommunityList() {

        List<Community> communityList = communityRepository.findAllByOrderByCreatedAtDesc();

        return new ResponseEntity<>(communityList, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getCommunity(Long id){

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));

        return new ResponseEntity<>(community, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> updateCommunity(CommunityRequestDto requestDto, Long id) throws IOException{

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));
        community.update(requestDto, s3UploadService, dir);

        return new ResponseEntity<>(community, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> deleteCommunity(Long id){

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));
        communityRepository.delete(community);

        return new ResponseEntity<>("커뮤니티 글 삭제 완료.", HttpStatus.OK);
    }
}
