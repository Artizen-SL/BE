package com.example.artizen.dto.response;

import com.example.artizen.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Boolean importance;
    private String createdAt;

    public NotificationResponseDto(Notification notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.imageUrl = notification.getImageUrl();
        this.importance = notification.getImportance();
        this.createdAt = notification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
