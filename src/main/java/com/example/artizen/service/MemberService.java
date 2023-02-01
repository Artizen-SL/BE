package com.example.artizen.service;

import com.example.artizen.dto.response.KakaoMemberInfoDto;
import com.example.artizen.entity.Member;
import com.example.artizen.entity.MemberGenderEnum;
import com.example.artizen.entity.MemberRoleEnum;
import com.example.artizen.repository.MemberRepository;
import com.example.artizen.security.MemberDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    @Value("${kakaoAPI}")
    private String kakaoAPI;

    @Value("${redirectUri}")
    private String redirectUri;

    //기본 회원가입
//    public void registerUser(SignupRequestDto signupRequestDto) {
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
//    }

    //카카오 로그인
    public void kakaoLogin(String accessToken) throws JsonProcessingException {
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getAccessToken(code);

        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoMemberInfoDto kakaoMemberInfoDto = getKakaoMemberInfo(accessToken);

        // 3. "카카오 사용자 정보"로 필요시 회원가입
        Member kakaoMember = registerKakaoUserIfNeeded(kakaoMemberInfoDto);

        MemberDetailsImpl memberDetails = new MemberDetailsImpl(kakaoMember);

        // 4. 강제 로그인 처리
        forceLogin(kakaoMember);
    }

//    private String getAccessToken(String code) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", kakaoAPI);
//        body.add("redirect_uri", redirectUri);
//        body.add("code", code);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        String accessToken = jsonNode.get("access_token").asText();
//
//        return accessToken;
//    }

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
        Boolean isAgeRange = jsonNode.get("kakao_account")
                .get("has_age_range").asBoolean();

        String ageRange = "";
        if (isAgeRange) {
            ageRange = jsonNode.get("kakao_account")
                    .get("age_range").asText();
        } else {
            ageRange = "unknown";
        }

        //생일 정보
        Boolean isBirthday = jsonNode.get("kakao_account")
                .get("has_birthday").asBoolean();

        String birthday = "";
        if (isBirthday) {
            birthday = jsonNode.get("kakao_account")
                    .get("birthday").asText();
        } else {
            birthday = "unknown";
        }

        //성별 정보
        Boolean isGender = jsonNode.get("kakao_account")
                .get("has_gender").asBoolean();

        String gender = "";
        if (isGender) {
            gender = jsonNode.get("kakao_account")
                    .get("gender").asText();
        } else {
            gender = "unknown";
        }

        System.out.println();
        System.out.println("==========================================");
        System.out.println("카카오 사용자 정보: " + id + ", " + nickname);
        System.out.println("gender = " + gender);
        System.out.println("ageRange = " + ageRange);
        System.out.println("profileImgUrl = " + profileImgUrl);
        System.out.println("birthday = " + birthday);
        System.out.println("==========================================");
        System.out.println();

        return new KakaoMemberInfoDto(id, nickname, gender, ageRange, birthday, profileImgUrl);
    }

    private Member registerKakaoUserIfNeeded(KakaoMemberInfoDto kakaoMemberInfoDto) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String kakaoId = kakaoMemberInfoDto.getId();
        Member kakaoUser = memberRepository.findById(kakaoId)
                .orElse(null);
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

            kakaoUser = new Member(kakaoId, nickname, encodedPassword, role, gender, ageRange, profileImgUrl, birthday);

            memberRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    private void forceLogin(Member kakaoMember) {
        MemberDetailsImpl memberDetails = new MemberDetailsImpl(kakaoMember);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String kakaoLogout(String accessToken) {

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

        return response.getBody();
    }
}
