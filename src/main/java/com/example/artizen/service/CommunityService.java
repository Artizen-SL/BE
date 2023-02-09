package com.example.artizen.service;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.dto.response.CommunityResponseDto;
import com.example.artizen.dto.response.MessageDto;
import com.example.artizen.entity.Community;
import com.example.artizen.entity.ResponseCode;
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

        return ResponseEntity.status(ResponseCode.POST_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.POST_SUCCESS.getCode(), ResponseCode.POST_SUCCESS.getMsg() ));
    }


    @Transactional
    public ResponseEntity<?> getCommunityList() {

        List<Community> communityList = communityRepository.findAllByOrderByCreatedAtDesc();

        return new ResponseEntity<>(communityList, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getCommunity(Long id){

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));


        return new ResponseEntity<>(new CommunityResponseDto(community), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> updateCommunity(CommunityRequestDto requestDto, Long id) throws IOException{

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));
        community.update(requestDto, s3UploadService, dir);

        return ResponseEntity.status(ResponseCode.PUT_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.PUT_SUCCESS.getCode(), ResponseCode.PUT_SUCCESS.getMsg()));
    }


    @Transactional
    public ResponseEntity<?> deleteCommunity(Long id){

        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다."));
        communityRepository.delete(community);

        return ResponseEntity.status(ResponseCode.DELETE_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.DELETE_SUCCESS.getCode(), ResponseCode.DELETE_SUCCESS.getMsg()));
    }
}
