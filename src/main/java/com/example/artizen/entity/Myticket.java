package com.example.artizen.entity;

import com.example.artizen.dto.request.MypageRequestDto;
import com.example.artizen.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Myticket extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String review;
    private String ticketImg;
    private Integer star;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "artizenId")
    private Artizen artizen;

    public Myticket (Member member, Artizen artizen,  MypageRequestDto mypageRequestDto, String imgUrl) {
        this.review = mypageRequestDto.getReview();
        this.ticketImg = imgUrl;
        this.star = mypageRequestDto.getStar();
        this.member = member;
        this.artizen = artizen;
    }

    public void update(MypageRequestDto mypageRequestDto, String imgUrl){
        this.review = mypageRequestDto.getReview();
        this.ticketImg = imgUrl;
        this.star = mypageRequestDto.getStar();
    }
}
