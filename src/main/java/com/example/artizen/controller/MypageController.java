package com.example.artizen.controller;

import com.example.artizen.dto.request.MypageRequestDto;
import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.MypageService;
import com.example.artizen.util.TimeStamped;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Api(tags = "Artizen mypage (마이페이지)")
public class MypageController extends TimeStamped {

    private final MypageService mypageService;

    @Operation(summary = "마이페이지 메인", description = "마이페이지 메인 유저 닉네임, 유저 프로필 조회 기능")
    @GetMapping(value = "/mypage")
    public ResponseEntity<?> mypage (@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.mypage(memberDetails.getMember());
    }

    @Operation(summary = "마이페이지 좋아요 조회", description = "마이페이지 유저의 좋아요 리스트 조회 기능")
    @GetMapping(value = "/hearts")
    public ResponseEntity<?> getHearts (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @RequestParam(value = "page") Integer page,
                                        @RequestParam(value = "size") Integer size) {
        Integer pageTemp = page -1;
        return mypageService.getHearts(memberDetails.getMember(), pageTemp, size);
    }

    @Operation(summary = "마이페이지 커뮤니티 조회", description = "마이페이지 유저가 작성한 커뮤니티 글 조회 기능")
    @GetMapping(value = "/community")
    public ResponseEntity<?> getCommunity (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                           @RequestParam(value = "page") int page,
                                           @RequestParam(value = "size") int size) {
        int pageTemp = page -1;
        return mypageService.getCommunity(memberDetails.getMember(), pageTemp, size);
    }

    @Operation(summary = "마이페이지 마이티켓 작성", description = "마이페이지 마이티켓 작성(기록하기) 기능")
    @PostMapping(value = "/ticket/write")
    public ResponseEntity<?> writeMyticket (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @ModelAttribute MypageRequestDto mypageRequestDto) throws IOException {
        return mypageService.writeMyticket(memberDetails.getMember(), mypageRequestDto);
    }

    @Operation(summary = "마이페이지 마이티켓 리스트 조회", description = "마이페이지 유저가 작성한 마이티켓 리스트 조회 기능")
    @GetMapping(value = "/ticket/list")
    public ResponseEntity<?> getMytickets (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                           @RequestParam(value = "page") int page,
                                           @RequestParam(value = "size") int size) {
        int pageTemp = page -1;
        return mypageService.getMytickets(memberDetails.getMember(), pageTemp, size);
    }

    @Operation(summary = "마이페이지 마이티켓 수정", description = "마이페이지 유저가 작성한 각 마이티켓별 수정 기능")
    @PutMapping(value = "/ticket/{myTicket_id}")
    public ResponseEntity<?> updateMyTicket(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @PathVariable(name = "myTicket_id") Long id,
                                            @ModelAttribute MypageRequestDto mypageRequestDto) throws IOException{

        return mypageService.updateMyTicket(memberDetails.getMember(), id, mypageRequestDto);
    }

    @Operation(summary = "마이페이지 마이티켓 삭제", description = "마이페이지 유저가 작성한 각 마이티켓별 삭제 기성")
    @DeleteMapping(value = "/ticket/{myTicket_id}")
    public ResponseEntity<?> deleteMyTicket(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @PathVariable(name = "myTicket_id") Long id){

        return mypageService.deleteMyTicket(memberDetails.getMember(), id);
    }
}
