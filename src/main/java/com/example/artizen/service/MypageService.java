package com.example.artizen.service;

import com.example.artizen.dto.response.MypageResponseDto;
import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.Community;
import com.example.artizen.entity.ArtizenHeart;
import com.example.artizen.entity.Member;
import com.example.artizen.repository.ArtizenRepository;
import com.example.artizen.repository.CommunityRepository;
import com.example.artizen.repository.ArtizenHeartRepository;
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
    private final ArtizenHeartRepository artizenHeartRepository;
    private final ArtizenRepository artizenRepository;
    private final CommunityRepository communityRepository;


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
        Slice<ArtizenHeart> hearts = artizenHeartRepository.findHeartsByMember_Id(member.getId(), pageable);

        List<MypageResponseDto> myHearts = new ArrayList<>();
        for (ArtizenHeart artizenHeartList : hearts) {

            if(hearts.isEmpty()) {
                return new ResponseEntity<>("좋아요한 컨텐츠가 없습니다.", HttpStatus.BAD_REQUEST);
            }

            String content = artizenHeartList.getArtizen().getId();

            Artizen heartContent = artizenRepository.findById(content).orElseThrow(
                    () -> new IllegalArgumentException("해당 컨텐츠 정보가 없습니다.")
            );

            MypageResponseDto mypageResponseDto = new MypageResponseDto(heartContent);

            myHearts.add(mypageResponseDto);

        }

        return new ResponseEntity<>(myHearts, HttpStatus.OK);
    }

    public ResponseEntity<?> getCommunity(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Community> communities = communityRepository.findByMember_Id(member.getId(), pageable);

        if(communities.isEmpty()) {
            return new ResponseEntity<>("작성한 커뮤니티 글이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<MypageResponseDto> myCommunities = new ArrayList<>();
        for (Community communityList : communities) {
            myCommunities.add(new MypageResponseDto(communityList));
        }

        return new ResponseEntity<>(myCommunities, HttpStatus.OK);
    }

}
