package com.example.artizen.service;

import com.example.artizen.dto.request.MypageRequestDto;
import com.example.artizen.dto.response.MypageResponseDto;
import com.example.artizen.entity.*;
import com.example.artizen.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final MyticketRepository myticketRepository;
    private final S3UploadService s3UploadService;


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
                return new ResponseEntity<>("좋아요한 컨텐츠가 없습니다.", HttpStatus.NO_CONTENT);
            }

            String content = artizenHeartList.getArtizen().getId();

            Artizen heartContent = artizenRepository.findById(content).orElseThrow(
                    () -> new IllegalArgumentException("해당 컨텐츠 정보가 없습니다.")
            );

            myHearts.add(new MypageResponseDto(heartContent));
        }

        return new ResponseEntity<>(myHearts, HttpStatus.OK);
    }

    public ResponseEntity<?> getCommunity(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Community> communities = communityRepository.findByMember_Id(member.getId(), pageable);

        if(communities.isEmpty()) {
            return new ResponseEntity<>("작성한 커뮤니티 글이 없습니다.", HttpStatus.NO_CONTENT);
        }

        List<MypageResponseDto> myCommunities = new ArrayList<>();
        for (Community communityList : communities) {
            myCommunities.add(new MypageResponseDto(communityList));
        }

        return new ResponseEntity<>(myCommunities, HttpStatus.OK);
    }

    public ResponseEntity<?> writeMyticket(Member member, MypageRequestDto mypageRequestDto) throws IOException {

        Optional<Member> memberCheck = memberRepository.findById(member.getId());
        if(memberCheck.isEmpty()) {
            return new ResponseEntity<>("로그인이 필요한 서비스 입니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Artizen> artizen = artizenRepository.findById(mypageRequestDto.getArtizenId());
        if(artizen.isEmpty()) {
            return new ResponseEntity<>("해당 컨텐츠가 없습니다.", HttpStatus.NO_CONTENT);
        }

        String imgUrl = s3UploadService.upload(mypageRequestDto.getTicketImg(), "/myTicket");

        myticketRepository.save(new Myticket(member, artizen.get(), mypageRequestDto, imgUrl));

        return new ResponseEntity<>("마이티켓 등록 완료! 🤩", HttpStatus.OK);
    }

    public ResponseEntity<?> getMytickets(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Myticket> myTickets = myticketRepository.findByMember_Id(member.getId(), pageable);

        if(myTickets.isEmpty()) {
            return new ResponseEntity<>("기록한 마이티켓이 없습니다.", HttpStatus.NO_CONTENT);
        }

        List<MypageResponseDto> myTicketList = new ArrayList<>();
        for (Myticket myTicket : myTickets) {
            myTicketList.add(new MypageResponseDto(myTicket));
        }

        return new ResponseEntity<>(myTicketList, HttpStatus.OK);
    }
}
