package com.example.artizen.dto.request;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String id;
    private String nickname;
    private String password;
    private String profileImgUrl;
    private String gender;
    private String ageRange;
    private boolean admin = false;
    private String adminToken;
}
