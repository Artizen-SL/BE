package com.example.artizen.controller;

import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/members")
@Api(tags = "Artizen longin/logout (로그인/로그아웃)")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/kakao/callback")
    public String kakaoCallback() {
        return "로그인이 진행되는 중입니다.";
    }

    @GetMapping(value = "/kakaoLogin")
    public ResponseEntity<?> kakaoToken(@RequestParam String accessToken) throws JsonProcessingException {
        return memberService.kakaoLogin(accessToken);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> kakaoLogout(@RequestParam String accessToken) {
        return memberService.kakaoLogout(accessToken);
    }

//    @PostMapping(value = "/signup")
//    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
//        return memberService.signup(signupRequestDto);
//    }
//
//    @GetMapping(value = "/login")
//    public ResponseEntity<?> login() {
//        return memberService
//    }

    @GetMapping(value = "/admin")
    public ResponseEntity<?> getAdmin(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestParam String adminToken) {
        return memberService.getAdmin(memberDetails.getMember().getId(), adminToken);
    }
}
