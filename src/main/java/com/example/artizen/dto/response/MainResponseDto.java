package com.example.artizen.dto.response;

import com.example.artizen.entity.Artizen;
import lombok.Getter;

@Getter
public class MainResponseDto {

    private String contentId;
    private String title;
    private String date;
    private String place;
    private String posterUrl;


    public MainResponseDto (Artizen artizen) {
        this.contentId = artizen.getId();
        this.title = artizen.getName();
        this.date = artizen.getDate();
        this.place = artizen.getPlace();
        this.posterUrl = artizen.getPosterUrl();
    }
}
