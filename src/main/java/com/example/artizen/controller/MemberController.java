package com.example.artizen.controller;

import com.example.artizen.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    //회원 로그인 페이지
//    @GetMapping("/members/login")
//    public String login() {
//
//        memberService.kakaoLogin();
//
//        return "login";
//    }

//기본 회원가입 필요시 살려야할 부분.

//    //회원 가입 페이지
//    @GetMapping("/members/signup")
//    public String signup() {
//        return "signup";
//    }
//
//    //회원 가입 요청 처리
//    @PostMapping("/members/signup")
//    public String registerMember(SignupRequestDto requestDto) {
//        memberService.registerUser(requestDto);
//        return "redirect:/members/login";
//    }

    //카카오 로그인
//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//        // authorizedCode: 카카오 서버로부터 받은 인가 코드
//
//        return memberService.kakaoLogin(code);
//    }

    @GetMapping("/members/kakaoLogin")
    public void kakaoToken(@RequestParam String accessToken) throws JsonProcessingException {
        memberService.kakaoLogin(accessToken);
    }

    @GetMapping("/members/logout")
    public String kakaoLogout(@RequestParam String accessToken) {

        memberService.kakaoLogout(accessToken);

        return "로그아웃 성공";
    }
}
