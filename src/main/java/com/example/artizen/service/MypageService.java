package com.example.artizen.service;

import com.example.artizen.dto.response.MypageResponseDto;
import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.Heart;
import com.example.artizen.entity.Member;
import com.example.artizen.repository.ArtizenRepository;
import com.example.artizen.repository.HeartRepository;
import com.example.artizen.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;
    private final ArtizenRepository artizenRepository;


    public ResponseEntity<?> mypage(Member member) {
        Optional<Member> memberInfo = memberRepository.findById(member.getId());

        if (memberInfo.isEmpty()) {
            return new ResponseEntity<>("가입 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        MypageResponseDto mypageResponseDto = new MypageResponseDto(memberInfo.get());

        return new ResponseEntity<> (mypageResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<?> getHearts(Member member, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Heart> hearts = heartRepository.findHeartsByMember_Id(member.getId(), pageable);

        List<MypageResponseDto> myHearts = new ArrayList<>();
        for (Heart heartList : hearts) {

            if(hearts.isEmpty()) {
                return new ResponseEntity<>("좋아요한 컨텐츠가 없습니다.", HttpStatus.BAD_REQUEST);
            }

            String content = heartList.getArtizen().getId();

            Artizen heartContent = artizenRepository.findById(content).orElseThrow(
                    () -> new IllegalArgumentException("해당 컨텐츠 정보가 없습니다.")
            );

            MypageResponseDto mypageResponseDto = new MypageResponseDto(heartContent);

            myHearts.add(mypageResponseDto);

        }

        return new ResponseEntity<>(myHearts, HttpStatus.OK);
    }
}
