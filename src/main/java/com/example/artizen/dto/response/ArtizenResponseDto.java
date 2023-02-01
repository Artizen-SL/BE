package com.example.artizen.dto.response;

import com.example.artizen.entity.Artizen;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArtizenResponseDto {
    private String id;
    private String name;
    private String genre;
    private String state;
    private String startDate;
    private String endDate;
    private String posterUrl;
    private String facility;
    private String Date;
    private String price;
    private String content;
    private String staff;

    public ArtizenResponseDto(String id, String name, String genre, String state,
                              String startDate, String endDate, String posterUrl, String facility) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.posterUrl = posterUrl;
        this.facility = facility;
    }

    public ArtizenResponseDto(String id, String name, String genre, String state,
                              String startDate, String endDate, String posterUrl,
                              String facility, String price, String content, String staff) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.posterUrl = posterUrl;
        this.facility = facility;
        this.price = price;
        this.content = content;
        this.staff = staff;
    }

    public ArtizenResponseDto(Artizen artizen) {
        this.id = artizen.getId();
        this.name = artizen.getName();
//        this.genre = artizen.getCategory();
//        this.state = artizen.getStatus();
        this.Date = artizen.getDate();
        this.posterUrl = artizen.getPosterUrl();
        this.facility = artizen.getPlace();
    }
}
