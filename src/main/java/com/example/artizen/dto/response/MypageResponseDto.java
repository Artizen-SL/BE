package com.example.artizen.dto.response;

import com.example.artizen.entity.Artizen;
import com.example.artizen.entity.Community;
import com.example.artizen.entity.Member;
import com.example.artizen.entity.Myticket;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MypageResponseDto {

    private String id;
    private String nickname;
    private String profileImg;

    //아티즌 관련 필드
    private String cultureName;
    private String place;
    private String date;

    //커뮤니티 관련 필드
    private Long communityId;
    private String title;
    private String content;
    private String tag;
    private String createdAt;

    //마이티켓 관련 필드
    private Long myTicketId;
    private String ticketImg;
    private Integer totalHeart;


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

    public MypageResponseDto (Community community) {
        this.communityId = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.tag = community.getTag();
        this.createdAt = community.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public MypageResponseDto (Myticket myticket) {
        this.myTicketId = myticket.getId();
        this.cultureName = myticket.getArtizen().getName();
        this.place = myticket.getArtizen().getPlace();
        this.date = myticket.getArtizen().getDate();
        this.ticketImg = myticket.getTicketImg();
        this.totalHeart = myticket.getArtizen().getArtizenHeartList().size();
    }
}
