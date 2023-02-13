package com.example.artizen.entity;

import com.example.artizen.dto.response.ArtizenResponseDto;
import com.example.artizen.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Artizen extends TimeStamped {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String posterUrl;

    @Column
    private String price;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column
    private String staff;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String category;

    private String tag;

    @Column(nullable = false)
    private String status;

    private double[] location;

    @OneToMany(mappedBy = "artizen", cascade = CascadeType.REMOVE)
    private List<Image> imageList;

    @OneToMany(mappedBy = "artizen", cascade = CascadeType.REMOVE)
    private List<Time> timeList;

    @OneToMany(mappedBy = "artizen", cascade = CascadeType.REMOVE)
    private List<ArtizenHeart> artizenHeartList;

    @OneToMany(mappedBy = "artizen", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;


    public Artizen(ArtizenResponseDto artizenResponseDto) {
        this.id = artizenResponseDto.getId();
        this.name = artizenResponseDto.getName();
        this.place = artizenResponseDto.getFacility();
        this.posterUrl = artizenResponseDto.getPosterUrl();
        this.date = artizenResponseDto.getStartDate() + " ~ " + artizenResponseDto.getEndDate();
        this.category = artizenResponseDto.getGenre();
        this.status = artizenResponseDto.getState();
        this.location = artizenResponseDto.getLocation();
    }

    public void update(ArtizenResponseDto artizenResponseDto) {
        this.id = artizenResponseDto.getId();
        this.name = artizenResponseDto.getName();
        this.place = artizenResponseDto.getFacility();
        this.posterUrl = artizenResponseDto.getPosterUrl();
        this.price = artizenResponseDto.getPrice();
        this.content = artizenResponseDto.getContent();
        this.staff = artizenResponseDto.getStaff();
        this.date = artizenResponseDto.getStartDate() + " ~ " + artizenResponseDto.getEndDate();
        this.category = artizenResponseDto.getGenre();
        this.status = artizenResponseDto.getState();
    }
}
