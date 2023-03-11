package com.example.artizen.controller;

import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "카카오 로그인 callback 링크", description = "카카오 로그인 callback시 return 값")
    @GetMapping(value = "/kakao/callback")
    public String kakaoCallback() {
        return "로그인이 진행되는 중입니다.";
    }

    @Operation(summary = "카카오 로그인", description = "인가 코드로 카카오 로그인 진행 기능")
    @GetMapping(value = "/kakaoLogin")
    public ResponseEntity<?> kakaoToken(@RequestParam String code) throws JsonProcessingException {
        return memberService.kakaoLogin(code);
    }

    @Operation(summary = "카카오 로그아웃", description = "accessToken으로 카카오 로그아웃 진행 기능")
    @GetMapping(value = "/kakaoLogout")
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

    @Operation(summary = "관리자 권한 추가", description = "관리자 key 인증으로 관리자 권한으로 변경해주는 기능")
    @GetMapping(value = "/admin")
    public ResponseEntity<?> getAdmin(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestParam String adminToken) {
        return memberService.getAdmin(memberDetails.getMember().getId(), adminToken);
    }
}
