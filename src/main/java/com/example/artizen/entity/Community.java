package com.example.artizen.entity;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.service.S3UploadService;
import com.example.artizen.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor
public class Community extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    private String tag;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public Community(CommunityRequestDto requestDto, Member member, S3UploadService s3UploadService, String dir) throws IOException {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = s3UploadService.upload(requestDto.getImageUrl(), dir, requestDto.getTitle());
        this.tag = requestDto.getTag();
        this.member = member;
    }

    public void update(CommunityRequestDto requestDto, Member member, S3UploadService s3UploadService, String dir) throws IOException {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = s3UploadService.upload(requestDto.getImageUrl(), dir, requestDto.getTitle());
        this.tag = requestDto.getTag();
        this.member = member;
    }

}
