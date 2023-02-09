package com.example.artizen.entity;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.service.S3UploadService;
import com.example.artizen.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor
public class Community extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String tag;

    public Community(CommunityRequestDto requestDto, S3UploadService s3UploadService, String dir) throws IOException {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = s3UploadService.upload(requestDto.getImageUrl(), dir, requestDto.getTitle());
        this.tag = requestDto.getTag();
    }

    public void update(CommunityRequestDto requestDto, S3UploadService s3UploadService, String dir) throws IOException{
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = s3UploadService.upload(requestDto.getImageUrl(), dir, requestDto.getTitle());
        this.tag = requestDto.getTag();
    }

}
