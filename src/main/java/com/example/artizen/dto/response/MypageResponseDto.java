package com.example.artizen.dto.response;

import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MypageResponseDto {

    private String id;
    private String nickname;
    private String profileImg;
    private String cultureName;
    private String place;
    private String date;
    private Integer totalHearts;

    public MypageResponseDto (Member member) {
        this.nickname = member.getNickname();
        this.profileImg = member.getProfileImgUrl();
    }

    public MypageResponseDto (Artizen artizen) {
        this.id = artizen.getId();
        this.cultureName = artizen.getName();
        this.place = artizen.getPlace();
        this.date = artizen.getDate();
    }
}
