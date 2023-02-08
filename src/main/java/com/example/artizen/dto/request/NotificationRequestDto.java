package com.example.artizen.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class NotificationRequestDto {

    private String title;
    private String content;
    private MultipartFile imageUrl;
    private String importance;
}
