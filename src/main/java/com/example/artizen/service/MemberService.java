package com.example.artizen.service;

import com.example.artizen.dto.response.KakaoMemberInfoDto;
import com.example.artizen.entity.Member;
import com.example.artizen.entity.MemberGenderEnum;
import com.example.artizen.entity.MemberRoleEnum;
import com.example.artizen.jwt.JwtAuthFilter;
import com.example.artizen.jwt.TokenDto;
import com.example.artizen.jwt.TokenProvider;
import com.example.artizen.repository.MemberRepository;
import com.example.artizen.security.MemberDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    @Value("${kakaoAPI}")
    private String kakaoAPI;

    @Value("${redirectUri}")
    private String redirectUri;

    //카카오 로그인
    public ResponseEntity<?> kakaoLogin(String code) throws JsonProcessingException {
        //"인가 코드"로 "엑세스 토큰" 요청
        String accessToken = getAccessToken(code);

        //"액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoMemberInfoDto kakaoMemberInfoDto = getKakaoMemberInfo(accessToken);

        //"카카오 사용자 정보"로 필요시 회원가입
        Member kakaoMember = registerKakaoUserIfNeeded(kakaoMemberInfoDto);

        MemberDetailsImpl memberDetails = new MemberDetailsImpl(kakaoMember);

        UsernamePasswordAuthenticationToken toAuthentication = new UsernamePasswordAuthenticationToken(memberDetails.getSubId(), memberDetails.getPassword());

        TokenDto tokenDto = tokenProvider.generateTokenDto(toAuthentication);

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add(JwtAuthFilter.AUTHORIZATION_HEADER , JwtAuthFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        httpHeaders.add("Refresh-Token" , tokenDto.getRefreshToken());

        //강제 로그인 처리
        forceLogin(kakaoMember);

        return new ResponseEntity<>("로그인 완료", httpHeaders, HttpStatus.OK);
    }

    //액세스 토큰 요청
    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoAPI);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken = jsonNode.get("access_token").asText();

        return accessToken;
    }

    //토큰으로 카카오 API 호출
    private KakaoMemberInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String profileImgUrl = jsonNode.get("properties")
                .get("profile_image").asText();

        //연령대 정보
        boolean isAgeRange = jsonNode.get("kakao_account")
                .get("has_age_range").asBoolean();

        boolean ageAgreement = jsonNode.get("kakao_account")
                .get("age_range_needs_agreement").asBoolean();

        String ageRange = "";
        if (isAgeRange && !ageAgreement) {
            ageRange = jsonNode.get("kakao_account")
                    .get("age_range").asText();
        } else {
            ageRange = "unknown";
        }

        //생일 정보
        boolean isBirthday = jsonNode.get("kakao_account")
                .get("has_birthday").asBoolean();

        boolean birthdayAgreement = jsonNode.get("kakao_account")
                .get("birthday_needs_agreement").asBoolean();

        String birthday = "";
        if (isBirthday && !birthdayAgreement) {
            birthday = jsonNode.get("kakao_account")
                    .get("birthday").asText();
        } else {
            birthday = "unknown";
        }

        //성별 정보
        boolean isGender = jsonNode.get("kakao_account")
                .get("has_gender").asBoolean();

        boolean genderAgreement = jsonNode.get("kakao_account")
                .get("gender_needs_agreement").asBoolean();

        String gender = "";
        if (isGender && !genderAgreement) {
            gender = jsonNode.get("kakao_account")
                    .get("gender").asText();
        } else {
            gender = "unknown";
        }

        String subId = UUID.randomUUID().toString();

        return new KakaoMemberInfoDto(id, nickname, gender, ageRange, birthday, profileImgUrl, subId);
    }

    private Member registerKakaoUserIfNeeded(KakaoMemberInfoDto kakaoMemberInfoDto) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String kakaoId = kakaoMemberInfoDto.getId();
        Member kakaoUser = memberRepository.findById(kakaoId).orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String nickname = kakaoMemberInfoDto.getNickname();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // role: 일반 사용자
            MemberRoleEnum role = MemberRoleEnum.USER;

            // gender: 사용자 성별
            MemberGenderEnum gender = MemberGenderEnum.valueOf(kakaoMemberInfoDto.getGender());

            String ageRange = kakaoMemberInfoDto.getAgeRange();
            String profileImgUrl = kakaoMemberInfoDto.getProfileImgUrl();
            String birthday = kakaoMemberInfoDto.getBirthday();
            String subId = UUID.randomUUID().toString();

            kakaoUser = new Member (kakaoId, nickname, encodedPassword, role, gender, ageRange, profileImgUrl, birthday, subId);

            memberRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    private void forceLogin(Member kakaoMember) {
        MemberDetailsImpl memberDetails = new MemberDetailsImpl(kakaoMember);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public ResponseEntity<?> kakaoLogout(String accessToken) {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                kakaoLogoutRequest,
                String.class
        );

        return new ResponseEntity<> (response.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<?> getAdmin(String id, String adminToken) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isEmpty()) {
            return new ResponseEntity<>("해당 유저가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (Objects.equals(ADMIN_TOKEN, adminToken)) {
            member.get().updateAuth(MemberRoleEnum.ADMIN);
            memberRepository.save(member.get());

        } else {
            return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("관리자 설정 완료", HttpStatus.OK);
    }

    //기본 회원가입
//    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
//
//        // 회원 ID 중복 확인
//        String nickname = signupRequestDto.getNickname();
//        Optional<Member> found = memberRepository.findByNickname(nickname);
//        if (found.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
//        }
//
//        // 패스워드 암호화
//        String password = passwordEncoder.encode(signupRequestDto.getPassword());
//
//        // 사용자 ROLE 확인
//        MemberRoleEnum role = MemberRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = MemberRoleEnum.ADMIN;
//        }
//
//        //일반 회원 Id 확인
//        String id = signupRequestDto.getId();
//
//        Member member = new Member(nickname, password, role, id, gender, ageRange, profileImgUrl, location);
//        memberRepository.save(member);
//
//        return new ResponseEntity<> ("회원가입 완료", HttpStatus.OK);
//    }
}
