package com.example.artizen.dto.response;

import lombok.Getter;

@Getter
public class KakaoMemberInfoDto {
    private String id;
    private String nickname;
    private String profileImgUrl;
    private String gender;
    private String ageRange;
    private String birthday;

    public KakaoMemberInfoDto (String id, String nickname, String gender, String ageRange, String birthday, String profileImgUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.ageRange = ageRange;
        this.birthday = birthday;
    }

}
