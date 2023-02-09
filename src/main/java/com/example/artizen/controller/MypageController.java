package com.example.artizen.controller;

import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.MypageService;
import com.example.artizen.util.TimeStamped;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MypageController extends TimeStamped {

    private final MypageService mypageService;

    @GetMapping(value = "/mypage")
    public ResponseEntity<?> mypage (@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.mypage(memberDetails.getMember());
    }

    //내가 좋아요한 공연
    @GetMapping(value = "/hearts")
    public ResponseEntity<?> getHearts (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @RequestParam(value = "page") Integer page,
                                        @RequestParam(value = "size") Integer size) {
        Integer pageTemp = page -1;
        return mypageService.getHearts(memberDetails.getMember(), pageTemp, size);
    }


}
