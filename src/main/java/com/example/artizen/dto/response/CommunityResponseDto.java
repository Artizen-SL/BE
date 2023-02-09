package com.example.artizen.dto.response;

import com.example.artizen.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String tag;
    private String createdAt;

    public CommunityResponseDto(Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.imageUrl = community.getImageUrl();
        this.tag = community.getTag();
        this.createdAt = community.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
