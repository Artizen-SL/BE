package com.example.artizen.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MypageRequestDto {
    private String artizenId;
    private MultipartFile ticketImg;
    private Integer star;
    private String review;

    public MypageRequestDto (String artizenId, MultipartFile ticketImg, Integer star, String review) {
        this.artizenId = artizenId;
        this.ticketImg = ticketImg;
        this.star = star;
        this.review = review;
    }
}
