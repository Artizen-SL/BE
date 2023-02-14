package com.example.artizen.controller;

import com.example.artizen.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/user/kakao/callback")
    public String  kakaoCallback() {
        return "로그인이 진행되는 중입니다.";
    }

    @GetMapping(value = "/members/kakaoLogin")
    public ResponseEntity<?> kakaoToken(@RequestParam String accessToken) throws JsonProcessingException {
        return memberService.kakaoLogin(accessToken);
    }

    @GetMapping(value = "/members/logout")
    public ResponseEntity<?> kakaoLogout(@RequestParam String accessToken) {
        return memberService.kakaoLogout(accessToken);
    }
}
